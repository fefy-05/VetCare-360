package com.vetcare360.service;
import com.vetcare360.domain.Usuario;
import com.vetcare360.dto.*;
import com.vetcare360.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Locale;
@Service
public class UsuarioService implements UserDetailsService {
 private final UsuarioRepository repo;private final PasswordEncoder encoder;
 public UsuarioService(UsuarioRepository r,PasswordEncoder e){repo=r;encoder=e;}
 @Transactional public void registrar(RegistroForm f){
  String correo=f.getCorreo().trim().toLowerCase(Locale.ROOT);
  if(repo.existsByCorreo(correo))throw new IllegalArgumentException("Este correo ya está registrado.");
  Usuario u=new Usuario();u.setNombre(f.getNombre().trim());u.setCorreo(correo);
  u.setPasswordHash(encoder.encode(f.getPassword()));u.setRol("CLIENTE");repo.save(u);
 }
 @Transactional(readOnly=true) public List<Usuario> listar(){
  return repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
 }
 @Transactional(readOnly=true) public String nombreDe(String correo){
  return repo.findByCorreo(correo).map(Usuario::getNombre).orElse(correo);
 }
 @Transactional public void cambiarRol(Long id,String rol,String correoActual){
  if(!rol.equals("ADMIN")&&!rol.equals("CLIENTE"))throw new IllegalArgumentException("Rol no válido.");
  Usuario u=repo.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado."));
  if(u.getCorreo().equals(correoActual))throw new IllegalArgumentException("No puedes cambiar tu propio rol.");
  u.setRol(rol);repo.save(u);
 }
 @Transactional public void eliminar(Long id,String correoActual){
  Usuario u=repo.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado."));
  if(u.getCorreo().equals(correoActual))throw new IllegalArgumentException("No puedes eliminar tu propia cuenta.");
  repo.delete(u);
 }
 @Override public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
  Usuario u=repo.findByCorreo(correo.toLowerCase(Locale.ROOT)).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));
  return User.withUsername(u.getCorreo()).password(u.getPasswordHash()).roles(u.getRol()).build();
 }
 @Transactional public void cambiarClave(String correo,CambioClaveForm f){
  Usuario u=repo.findByCorreo(correo).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado."));
  if(!encoder.matches(f.getActual(),u.getPasswordHash()))throw new IllegalArgumentException("La contraseña actual es incorrecta.");
  if(!f.getNueva().equals(f.getConfirmar()))throw new IllegalArgumentException("Las nuevas contraseñas no coinciden.");
  if(encoder.matches(f.getNueva(),u.getPasswordHash()))throw new IllegalArgumentException("La nueva contraseña debe ser diferente.");
  u.setPasswordHash(encoder.encode(f.getNueva()));repo.save(u);
 }
}

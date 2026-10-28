package com.vetcare360.service;
import com.vetcare360.domain.Usuario;
import com.vetcare360.dto.*;
import com.vetcare360.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
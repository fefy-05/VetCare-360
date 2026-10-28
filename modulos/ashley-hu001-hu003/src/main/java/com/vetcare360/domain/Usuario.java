package com.vetcare360.domain;
import jakarta.persistence.*;
@Entity @Table(name="usuarios")
public class Usuario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=100) private String correo;
 @Column(nullable=false,length=100) private String nombre;
 @Column(nullable=false) private String passwordHash;
 @Column(nullable=false) private String rol="CLIENTE";
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getCorreo(){return correo;} public void setCorreo(String v){correo=v;}
 public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
 public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public String getRol(){return rol;} public void setRol(String v){rol=v;}
}
package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.DoctorAccountDTO;
import org.example.privateclinicwebsitespringboot.DTO.SignUpDTO;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Model.Patient;
import org.example.privateclinicwebsitespringboot.Repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private MyUserRepository myUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserRepository.findByUsername(username);
        if(user.isPresent()) {
            var userObject = user.get();
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(userObject.getRole()));
            return new org.springframework.security.core.userdetails.User(
                    userObject.getUsername(),
                    userObject.getPassword(),
                    authorities
            );
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    public Optional<MyUser> findUserExistedByUserName(String username){ return myUserRepository.findByUsername(username); }
    public Optional<MyUser> findUserExistedByEmail(String email){
        return myUserRepository.findByEmail(email);
    }

    public MyUser loadMyUserByUsername(String username){
        Optional<MyUser> user = myUserRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    public MyUser signUpPatient(SignUpDTO signUpDTO, PasswordEncoder passwordEncoder, Patient patient){
        MyUser myUser = new MyUser();
        myUser.setUsername(signUpDTO.getUsername());
        myUser.setEmail(signUpDTO.getEmail());
        myUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        myUser.setRole("USER");
        myUser.setPatient(patient);
        myUserRepository.save(myUser);
        return myUser;
    }
    public MyUser signUpDoctor(DoctorAccountDTO doctorAccountDTO, PasswordEncoder passwordEncoder,Doctor doctor){
        MyUser myUser = new MyUser();
        myUser.setUsername(doctorAccountDTO.getUsername());
        myUser.setEmail(doctorAccountDTO.getEmail());
        myUser.setPassword(passwordEncoder.encode(doctorAccountDTO.getPassword()));
        myUser.setRole("DOCTOR");
        myUser.setDoctor(doctor);
        myUserRepository.save(myUser);
        return myUser;
    }

    public Optional<String> findEmailByPatientId(Long patientId){
        return myUserRepository.findEmailByPatientId(patientId);
    }
    public Optional<String> findEmailByDoctorId(Long doctorId){
        return myUserRepository.findEmailByDoctorId(doctorId);
    }
}

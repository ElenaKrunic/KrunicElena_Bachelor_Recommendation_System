package ftn.uns.diplomski.movierecommendationservice.controller;

import ftn.uns.diplomski.movierecommendationservice.dto.RoleDTO;
import ftn.uns.diplomski.movierecommendationservice.model.Role;
import ftn.uns.diplomski.movierecommendationservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllRoels() {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        roleRepository.findAll().forEach(role -> roleDTOList.add(getRoleDto(role)));
        return  ResponseEntity.ok().body(roleDTOList);
    }

    @PostMapping(value = "/insertRole")
    public ResponseEntity<Role> saveRole(@RequestBody RoleDTO roleDTO) {

        Role role = new Role();
        role.setName(roleDTO.getName());

        role = roleRepository.save(role);

        return new ResponseEntity<Role>(role, HttpStatus.CREATED);
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") Long id, @RequestBody Role role) {
        Role _role = roleRepository.findById(id).orElseThrow(null);

        _role.setId(role.getId());
        _role.setName(role.getName());

        return new ResponseEntity<>(roleRepository.save(_role), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") Long id) {
        roleRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    private RoleDTO getRoleDto(Role role) {
        return new RoleDTO(role.getId(), role.getName());
    }
}

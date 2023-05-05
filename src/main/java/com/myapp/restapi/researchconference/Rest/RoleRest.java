package com.myapp.restapi.researchconference.Rest;

import com.myapp.restapi.researchconference.Restservice.Impl.RoleRestServiceImpl;
import com.myapp.restapi.researchconference.Restservice.Interface.RoleRestService;
import com.myapp.restapi.researchconference.entity.Admin.Role;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class RoleRest {

    private final RoleRestService roleRestService;

    @Autowired
    public RoleRest(RoleRestServiceImpl roleRestServiceImpl) {
        this.roleRestService = roleRestServiceImpl;
    }

    @GetMapping("roles")
    public List<Role> findAll(){
        return roleRestService.findAll();
    }

    @GetMapping("roles/{roleName}")
    public Role findRoleByName(@PathVariable String roleName){
        Optional<Role> role = roleRestService.findRoleByName(roleName);
        if (role.isPresent()){
            return role.get();
        }else
            return null;
    }

    @PostMapping("roles")
    public ResponseEntity<String> add(@RequestBody Role role){
        Role temp = roleRestService.add(role);
        if (temp != null)
            return ResponseEntity.ok("Successfully added the New Role");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the Role");
    }

    @PutMapping("roles/{roleName}")
    public ResponseEntity<String> update(@RequestBody Role role, @PathVariable @NotBlank String roleName){
        Role role1 = roleRestService.update(role, roleName);
        if (role1 == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to Update the role");
        return ResponseEntity.ok("Successfully update the Role Description");
    }

    @DeleteMapping("roles/{roleName}")
    public ResponseEntity<String> delete(@PathVariable String roleName){
        boolean deleted = roleRestService.delete(roleName);

        if (deleted) {
            return ResponseEntity.ok("Successfully delete " + roleName + " Role");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
        }
    }

}

package com.myapp.restapi.researchconference.Rest;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.myapp.restapi.researchconference.DTO.ResetPasswordDTO;
import com.myapp.restapi.researchconference.DTO.UserDTO;
import com.myapp.restapi.researchconference.Exception.NoDataFoundException;
import com.myapp.restapi.researchconference.Restservice.Interface.RoleRestService;
import com.myapp.restapi.researchconference.Restservice.Interface.UserRestService;
import com.myapp.restapi.researchconference.Util.GetDataFromJWT;
import com.myapp.restapi.researchconference.entity.Admin.Role;
import com.myapp.restapi.researchconference.entity.Admin.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserRest {

    private final UserRestService userRestService;
    private final GetDataFromJWT getDataFromJWT;

    @Autowired
    public UserRest(UserRestService userRestService, GetDataFromJWT getDataFromJWT) {
        this.userRestService = userRestService;
        this.getDataFromJWT = getDataFromJWT;
    }

    @GetMapping("users")
    public List<UserDTO> findAll(@RequestParam int pageNumber, HttpServletRequest request) {
        int myUserID = getDataFromJWT.getID(request);
        return userRestService.findAll(pageNumber, myUserID);
    }

    @GetMapping("TotalUsers")
    public long getTotalUser(@RequestParam int isActive) {
        return userRestService.getTotalUser(isActive);
    }

    @GetMapping("users/nonActive")
    public List<UserDTO> findNonActive(int pageNumber){
        List<UserDTO> userList = userRestService.findNonActiveAccount(pageNumber);
        return userList;
    }

    @GetMapping("users/{roleName}")
    public List<User> findAllByRole(@PathVariable String roleName){
        return userRestService.findUserByRole(roleName);
    }

    @GetMapping("users/userID/{userID}")
    public UserDTO findByID(@PathVariable int userID){
        return userRestService.findByID(userID);
    }

    @GetMapping("users/username/{username}")
    public User findByUserName(@PathVariable String username){
        return userRestService.findByUserName(username);
    }

    @GetMapping("users/search/{username}")
    public List<User> searchByUsername(@PathVariable String username){
        return userRestService.searchByUsername(username);
    }

    @GetMapping("users/search/{role}/{username}")
    public List<User> searchByUsernameAndRole(@PathVariable("role") String roleName,
                                              @PathVariable("username") String username){
        return userRestService.searchByUsernameAndRole(username, roleName);
    }

    @DeleteMapping("users/{userID}")
    public void delete(@PathVariable int userID){
        userRestService.delete(userID);
    }

    @PostMapping("users")
    public ResponseEntity<Object> add(@Valid @RequestBody User user){
        User userData = userRestService.save(user);
        if (userData == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Username is used by other user");
        }
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .build(userData.getId());

        return ResponseEntity.created(url).build();
    }

    @PutMapping("users/{userID}")
    public ResponseEntity<String > update(@Valid @RequestBody User user,@PathVariable int userID){
        User userData = userRestService.update(user, userID);
        if (userData == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Username existed in DB");
        }
        return ResponseEntity.ok("Successfully Add");
    }

    @PatchMapping("users/activation/{userID}")
    public ResponseEntity<String> activation(@PathVariable int userID){
        userRestService.activation(userID);

        return ResponseEntity.ok("Successfully Activate the Account");
    }

    @PatchMapping("users/deactivation/{userID}")
    public ResponseEntity<String> deactivation(@PathVariable int userID){
        userRestService.deactivation(userID);

        return ResponseEntity.ok("Successfully Deactivate the Account");
    }

    @PostMapping("reset/password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        User user = userRestService.resetPassword(resetPasswordDTO);
        if (user == null)
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with that ID not found");

        return  ResponseEntity.ok("Successfully Reset the password");
    }

    private MappingJacksonValue filterOutPassword(User user){
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("password");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PasswordFilter", filter);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    private MappingJacksonValue filterOutPasswordList(List<User> user){
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("password");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PasswordFilter", filter);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }



}

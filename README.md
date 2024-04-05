The reason for few commit is because i create new repo when i want to upload   
to check all my commit (from start until my first commit in here), please check   
https://github.com/limlyang121/Research_Conference   



# MyProject
 Practicing Coding (Full Stack).


**To do:**
Fix some Bug (Some Alert not working in deploy and Navbar have larger height compared to localhost)      

**Completed Feauture:**  
1. Using Google Cloud Console-Google Drive as Server  
2. RBAC Security
3. JWT Security on both front-end and Back-end
4. Prevent IDOR (Insecure Direct Object Reference)
5. RBAC
6. Upload And Download Document to server
7. Etc (Some minor Stuff)    
8. Back-end Security (SecurityFilterChain and Cors Config)   


**Technologies Used:**  
[Spring, React.js, PostGreSQL, RESTful API]  
Security:  
[Spring Security, Json Web Token, RBAC, Bcrypt]  
Server:  
[Google Cloud Console -Google Drive]  
Other:  
[DTO, Git, Maven]  
Hosting:
[Heroku, Render]
Documentation:   
[Swagger]


Google Drive Link :  
https://drive.google.com/drive/folders/1aFIZ3sw9h159h8A46CwsKW1DG3OA7_8k?usp=share_link  

**Install**  
1. Java just run Maven with ```./mvnw spring-boot:run```  
2. For React.js, Run   
```npm install``` lastly, run with  ```npm start```   
Run this line if material/ui is causing problem   
```npm config set legacy-peer-deps true``` then do   
```npm install``` lastly, run with  ```npm start```   


***Set up***   

For PostgreSQL set-up, can skip since i'm already hosting in render    
but since it a free tier, speed might be slower than Local  
To setup locally, just need to grab the script name ```postgreSQL.sql``` in the project and use it.     
and change the datasource url, username and password in application properties  
![image](https://user-images.githubusercontent.com/103249985/235494341-8a9b3604-01e9-4e89-830d-cce8914eabe0.png)  



to change to Local Back-end, just switch the url in the ```AxiosUrl.js```   
![image](https://user-images.githubusercontent.com/103249985/236638817-7d4d666b-bbc0-47a7-b2a8-c5f2d415f0a6.png)   


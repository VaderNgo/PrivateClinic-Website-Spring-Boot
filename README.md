<h1 align="center">Private Clinic Web</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-green" alt="Spring Boot">
  <img src="https://img.shields.io/badge/MySQL-8.0-blue" alt="MySQL">
  <img src="https://img.shields.io/badge/Azure%20SQL%20DB-Deployed-blue" alt="Azure SQL DB">
  <img src="https://img.shields.io/badge/Hosted%20on-Render-orange" alt="Render">
  <img src="https://img.shields.io/badge/License-MIT-yellowgreen" alt="License">
</p>

<p align="center">
  <img style="width: 500px; height: 300px" src="img/login-screen.png" alt="login-screen">
  <img  style="width: 500px; height: 300px" src="img/admin-screen.png" alt="admin-screen">
</p>

<h2>🏥 Description</h2>
<p>
  Private Clinic Web is a web application for managing a private clinic, developed using Spring Boot and following the MVC (Model-View-Controller) design pattern. This application allows users to manage patient information, appointments, medical records, and various medical services. A MySQL database is used for data storage and is deployed on Azure SQL Database cloud. The application is hosted on Render to ensure scalability and high availability.
</p>

<h2>🌟 Features</h2>
<ul>
  <li><strong>Patient Management:</strong> Add, edit, delete patient</li>
  <li><strong>Appointment Management:</strong> Create, modify, cancel, and view appointments.</li>
  <li><strong>Medical Records Management:</strong> Store and retrieve patient medical information.</li>
  <li><strong>Bill Records Management:</strong> Store and retrieve patient bill information.</li>
  <li><strong>User Authentication and Authorization:</strong> Includes login/registration and role-based access control (doctor, patient, admin).</li>
</ul>

<h2>🛠 Technologies Used</h2>
<ul>
  <li><strong>Backend:</strong> Spring Boot <img src="https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white" alt="Spring Boot"></li>
  <li><strong>Frontend:</strong> Thymeleaf (or any suitable frontend framework) <img src="https://img.shields.io/badge/-Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white" alt="Thymeleaf"></li>
  <li><strong>Database:</strong> MySQL <img src="https://img.shields.io/badge/-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL"></li>
  <li><strong>Cloud Database:</strong> Azure SQL Database <img src="https://img.shields.io/badge/-Azure%20SQL%20Database-0078D4?style=flat-square&logo=microsoft-azure&logoColor=white" alt="Azure SQL Database"></li>
  <li><strong>Hosting:</strong> Render <img src="https://img.shields.io/badge/-Render-46E3B7?style=flat-square&logo=render&logoColor=white" alt="Render"></li>
</ul>

<h2>🚀 Installation and Deployment</h2>
<h3>Step 1: Clone this repo</h3></h3>

```bash
git clone https://github.com/VaderNgo/PrivateClinic-Website-Spring-Boot.git
```
<h3>Step 2: Configure the database</h3>

**NOTE: Update the MySQL configuration in the application.properties file.**

```bash
spring.datasource.url=jdbc:mysql://your-database-url
spring.datasource.username=your-username
spring.datasource.password=your-password
```

<h3>Step 3: Run the application (Local) </h3>

<p>In my case, I use Intelliji IDE so just wait for gradle build, then click the Start button</p>

<h3>Step 4: Deploy (Optional)</h3>
<p>Follow the instructions on the <a href="https://render.com/docs/deploy-spring-boot">Render Documentation</a> page to deploy the application.</p>


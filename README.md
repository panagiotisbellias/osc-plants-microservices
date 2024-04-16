# plants-microservices

## About The Project
The Plants App allows users to search for plants in database and add them to their user profile to be remainded about plant care routines. New users can register in the app by Google or login/password depending on preferences. Additional feature is live chat on one of the available topics.


## Registration
<img  src="https://github.com/asynoradzki/plants-microservices/blob/main/client/public/login.gif"  alt="github"  width="800px"  height="450px">

## Login with OAuth 2.0
<img  src="https://github.com/asynoradzki/plants-microservices/blob/main/client/public/oauth.gif"  alt="github"  width="800px"  height="450px">

## Adding Plant
<img  src="https://github.com/asynoradzki/plants-microservices/blob/main/client/public/addplant.gif"  alt="github"  width="800px"  height="450px">

## Chat with other people 
<img  src="https://github.com/asynoradzki/plants-microservices/blob/main/client/public/chat.gif"  alt="github"  width="800px"  height="450px">

## Technical Features
Frontend:
- Vite
- Axios with interceptors
- MUI
- Styled Components
- React Router
- useContext, useState, useEffect, useCallback, useMemo, useRef hooks
- Lodash - debounce
- StompJs
- SockJs-client
- Google reCaptcha

Backend:
- Microservices architecture
- Eureka Discovery server
- Api Gateway with Web Flux Spring Security Filter - JWT Token
- Spring Security - Username/Password registration/login + OAuth2.0 Google registration/login
- Google reCaptcha
- Kafka Message Broker - email notifications
- Websockets - live chat
- Zipkin
- PostgreSQL

## Application Presentation:

<img  src="https://github.com/ptatarczuk/Ideas/blob/main/client/src/assets/app.gif"  alt="github"  width="600px"  height="400px">

## Built With
Frontend: 

<a  href="https://www.typescriptlang.org/"  title="Typescript"><img  src="https://github.com/get-icon/geticon/raw/master/icons/typescript-icon.svg"  alt="Typescript"  width="50px"  height="50px"></a>
<a  href="https://reactjs.org/"  title="React"><img  src="https://github.com/get-icon/geticon/raw/master/icons/react.svg"  alt="React"  width="50px"  height="50px"></a>
<a  href="https://en.wikipedia.org/wiki/HTML5"  title="HTML"><img  src="https://github.com/get-icon/geticon/raw/master/icons/html-5.svg"  alt="HTML" height="50px"></a>
<a  href="https://en.wikipedia.org/wiki/CSS"  title="CSS"><img  src="https://github.com/get-icon/geticon/raw/master/icons/css-3.svg"  alt="CSS" height="50px"></a>
<a  href="https://material-ui.com/"  title="Material UI"><img  src="https://github.com/get-icon/geticon/raw/master/icons/material-ui.svg"  alt="Material UI"  width="50px"  height="50px"></a>
<a  href="https://code.visualstudio.com/"  title="Visual Studio Code"><img  src="https://github.com/get-icon/geticon/raw/master/icons/visual-studio-code.svg"  alt="Visual Studio Code"  width="50px"  height="50px"></a>
<a  href="https://www.npmjs.com/"  title="npm"><img  src="https://github.com/get-icon/geticon/raw/master/icons/npm.svg"  alt="npm"  width="50px"  height="50px"></a>

Backend:

<a  href="https://www.java.com/"  title="Java"><img  src="https://github.com/get-icon/geticon/raw/master/icons/java.svg"  alt="Java"  width="50px"  height="50px"></a>
<a  href="https://spring.io/"  title="Spring"><img  src="https://github.com/get-icon/geticon/raw/master/icons/spring.svg"  alt="Spring"  width="50px"  height="50px"></a>
<a  href="https://www.postgresql.org/"  title="PostgreSQL"><img  src="https://github.com/get-icon/geticon/raw/master/icons/postgresql.svg"  alt="PostgreSQL"  width="50px"  height="50px"></a>
<a  href="https://www.kafka.apache.org"  title="Kafka"><img  src="https://github.com/get-icon/geticon/raw/master/icons/kafka-icon.svg"  alt="Kafka"  width="50px"  height="50px"></a>
<a  href="https://www.jetbrains.com/idea/"  title="IntelliJ"><img  src="https://github.com/get-icon/geticon/raw/master/icons/intellij-idea.svg"  alt="IntelliJ"  width="50px"  height="50px"></a>

Other Technologies:

<a href="https://www.figma.com" title="figma"><img  src="https://github.com/get-icon/geticon/raw/master/icons/figma.svg"  alt="figma"  width="50px"  height="50px">
<a  href="https://discord.com/"  title="Discord"><img  src="https://github.com/get-icon/geticon/raw/master/icons/discord.svg"  alt="Discord"  width="50px"  height="50px"></a>
<a  href="https://git-scm.com/"  title="Git"><img  src="https://github.com/get-icon/geticon/raw/master/icons/git-icon.svg"  alt="Git"  width="50px"  height="50px"></a>
<a  href="https://github.com/"  title="github"><img  src="https://github.com/ptatarczuk/Ideas/blob/main/server/images/github.svg"  alt="github"  width="50px"  height="50px"></a>
<a  href="https://postman.com/"  title="postman"><img  src="https://github.com/get-icon/geticon/raw/master/icons/postman.svg"  alt="postman"  width="50px"  height="50px"></a>
<a  href="https://www.docker.com/"  title="docker"><img  src="https://github.com/get-icon/geticon/raw/master/icons/docker-icon.svg"  alt="docker"  width="50px"  height="50px"></a>
<a  href="https://trello.com/"  title="trello"><img  src="https://github.com/get-icon/geticon/raw/master/icons/trello.svg"  alt="trello"  width="50px"  height="50px"></a>

## Getting Started: 

### Prerequisites

Running the Application
To run this application on your local machine, follow these steps:

Install Git:
If you haven't already, make sure to install Git, the version control system, on your computer.

Clone the Repository:
Copy the project's repository URL and clone it to your local machine using the following command in your terminal:

git clone <repository-url>
Replace <repository-url> with the actual URL of the project (you can use either HTTPS or SSH).

### Installation

Install Dependencies:
Navigate to the project directory. On the client side, install the required dependencies using the following command:

npm install
On the server side, install the necessary dependencies as well.

Configure Backend:
Set up the backend of the application, including the application logic and the database (e.g., PostgreSQL). Make sure to configure the backend files and database connections as needed.

Run the Application:
After completing the above steps, you can now run the application. Start the server and client components to see the application in action.

Now, you should have the application up and running on your local machine. If you encounter any issues, refer to the project's documentation or contact the project maintainers for further assistance.

## Authors

Aleksander Synoradzki:

<a  href="https://github.com/asynoradzki"  title="github"><img  src="https://github.com/ptatarczuk/Ideas/blob/main/server/images/github.svg"  alt="github"  width="50px"  height="50px"></a><a  href="https://github.com/asynoradzki"  title="github"><img  src="https://github.com/get-icon/geticon/raw/master/icons/linkedin-icon.svg"  alt="github"  width="50px"  height="50px"></a> 

Piotr Tatarczuk:

<a  href="https://github.com/ptatarczuk"  title="github"><img  src="https://github.com/ptatarczuk/Ideas/blob/main/server/images/github.svg"  alt="github"  width="50px"  height="50px"></a><a  href="https://www.linkedin.com/in/ptatarczuk/"  title="github"><img  src="https://github.com/get-icon/geticon/raw/master/icons/linkedin-icon.svg"  alt="github"  width="50px"  height="50px"></a> 

<p align="right">(<a href="#readme-top">back to top</a>)</p>

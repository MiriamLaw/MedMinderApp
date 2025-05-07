# MedMinder

MedMinder is a medication tracking application that helps users manage their weekly medication schedules.

## Project Structure

The project is organized into two main directories:

- `backend`: Java Spring Boot application
- `frontend`: React application

## Backend (Java Spring Boot)

The backend is built with:

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL database
- Hibernate ORM

### Running the Backend

1. Navigate to the backend directory:
   \`\`\`
   cd backend
   \`\`\`

2. Build the application:
   \`\`\`
   ./mvnw clean package
   \`\`\`

3. Run the application:
   \`\`\`
   ./mvnw spring-boot:run
   \`\`\`

The backend will start on http://localhost:8080

## Frontend (React)

The frontend is built with:

- React 18
- React Router
- Tailwind CSS
- Plain JavaScript (no TypeScript)

### Running the Frontend (Development)

1. Navigate to the frontend directory:
   \`\`\`
   cd frontend
   \`\`\`

2. Install dependencies:
   \`\`\`
   npm install
   \`\`\`

3. Start the development server:
   \`\`\`
   npm start
   \`\`\`

The frontend will start on http://localhost:3000

### Building for Production

To build the frontend and copy it to the backend's static resources:

\`\`\`
cd frontend
npm run build
\`\`\`

This will automatically copy the build files to `backend/src/main/resources/static`.

## Deployment

For production deployment, you only need to deploy the backend application. The React frontend is served by the Spring Boot application from its static resources.

1. Build the frontend:
   \`\`\`
   cd frontend
   npm run build
   \`\`\`

2. Build the backend:
   \`\`\`
   cd backend
   ./mvnw clean package
   \`\`\`

3. Run the JAR file:
   \`\`\`
   java -jar target/medminder-0.0.1-SNAPSHOT.jar
   \`\`\`

## Features

- User authentication (login/register)
- Weekly medication tracking
- Support for up to 4 weeks
- Day-by-day medication management
- Medication alarms
- Admin dashboard for user management

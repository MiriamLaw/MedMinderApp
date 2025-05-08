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

AI Usage Section

```markdown
## 🤖 AI Code Assistant Usage

This project includes guidance for using an AI code assistant (like Cursor or ChatGPT with Cursor integration).

### 🔐 Boundaries & Guardrails
- The assistant **may scan the entire codebase** for context.
- It must **avoid making unrequested or destructive changes**.
- Changes should **respect existing environments (dev vs prod)**.
- **.env files must never be altered** unless explicitly instructed.
- It should **not run or restart backends/frontends**, and **must not deploy or push to Git**.

### 📦 After Making Front-End Edits
- Run:

```bash
npm run build
```

This ensures no unused code or issues remain from generated output.

### 🧹 Code Quality Expectations
- Code should be simple, DRY (don’t repeat yourself), and fully functional.
- Avoid files over 300 lines; suggest or implement refactoring as needed.
- Avoid introducing new tools, patterns, or frameworks unless necessary — and clean up old logic if you do.

### 🚫 Don’t...
- Add mock data to dev/prod environments (use test files only).
- Introduce placeholders or TODOs.

### 🧠 Assistant Roles
This project is full-stack. The assistant may be asked to:
- Write backend Java / Spring Boot code
- Help with Thymeleaf templates or controllers
- Generate or debug JavaScript / React logic
- Assist with full-stack integration (e.g. auth, REST, security config)
```
# MedMinder

MedMinder is a full-stack medication management app built with:

- **Backend:** Java 17, Spring Boot 3+, MySQL, Spring Security
- **Frontend:** React 18, JavaScript, Tailwind CSS
- **Tooling:** Cursor IDE with `.cursor-config` for AI development rules

---

## 📁 Project Structure

/medminder
├── backend/ # Spring Boot backend
├── frontend/ # React frontend (served from /static)
├── .cursor/ # Cursor IDE rules & config
└── README.md

## 🧠 AI Usage with Cursor

This project uses Cursor’s `.cursor-config` and reusable rule files to ensure safe, best-practice AI suggestions.

### ✅ .cursor-config

We use a centralized rule:

- `.cursor/rules/java-rules.mdc` — full-stack Java/React rules
- `defaultRules`: `"medminder-safety"`

This includes guardrails like:
- Never start servers without approval
- No mocking in prod/dev
- Never overwrite `.env`, `application.properties`, or `pom.xml`
- Avoid files over 300 lines
- Run `npm run build` after frontend edits

### 🔁 Reuse Across Projects

To reuse this setup in other projects:
1. Copy the `.cursor-config` to your root
2. Copy the `.cursor/rules/java-rules.mdc` file
3. Open Cursor → `Cmd+Shift+P` → "Reload Window"

---

## 🛠️ Dev Setup

### ▶️ Running the Backend

```bash
cd backend
./mvnw spring-boot:run

App runs at: http://localhost:8080

▶️ Running the Frontend (Dev)

cd frontend
npm install
npm start

Dev frontend: http://localhost:3000

cd frontend
npm run build

This will copy the compiled React app into backend/src/main/resources/static for Spring Boot to serve.

🚀 Deployment Notes
Only the backend needs to be deployed. It serves the built frontend from /static.

👀 Project Safety Guardrails
These are enforced by .cursor-config and apply to AI + dev work:

Never push, deploy, or restart without confirmation

Keep logic DRY and consistent across front/backend

No mocking data outside tests

Don’t modify system files without approval
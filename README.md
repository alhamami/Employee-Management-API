# **Employee Management System**

A **Spring Boot** application to manage employees, including features such as CRUD operations rate limiting, and audit logging.

---

## **Features**

- ✅ **CRUD Operations**: Create, read, update, and delete employee records.
- ⚡ **Rate Limiting**: Limits the number of requests to third-party APIs.
- 🛡️ **Custom Circuit Breaker**: Protects the system from failing third-party calls.
- 📜 **Audit Logging**: Tracks and logs key events during employee creation.
- 🐳 **Dockerized**: Ready-to-run in a containerized environment.

---

## **Prerequisites**

Make sure the following are installed on your system:

- ☕ **Java 23**
- 📦 **Maven** 
- 🐳 **Docker** (optional)

---

## **Getting Started**

### **Clone the Repository**

```bash
git clone https://github.com/alhamami/Employee-Management-API
cd employee-management
```
### **Build and Run**
Using Maven
- Build the JAR:
```bash
mvn clean package
```
- Run the Application:
```bash
java -jar target/employee-management-1.0.0.jar
```
---

## **API Endpoints**

### **Employee Management**

📋 **Retrieve All Employees**
- Endpoint: `GET /employees`
- Description: Fetches all employee records.
- Response: List of employees in JSON format.

🔍 **Retrieve Employee by ID**
- Endpoint: `GET /employees/{id}`
- Description: Fetches a specific employee by ID.
- Response: Employee details.

➕ **Create an Employee**
- Endpoint: `POST /employees`
- Description: Adds a new employee.
- Request Body:
```bash
{
  "firstName": "Jalal",
  "lastName": "Alhamami",
  "email": "jalal@outlook.com",
  "department": "IT"
}
```

✏️ **Update an Employee**
- Endpoint: `PUT /api/v1/employees/{id}`
- Description: Updates an existing employee by ID.
- Request Body:
```bash
{
  "firstName": "Jalal",
  "lastName": "Alhamami",
  "email": "jalal@outlook.com",
  "department": "HR"
}
```

🗑️ **Delete an Employee**
- Endpoint: `DELETE /employees/{id}`
- Description: Deletes an employee by ID.

---

## **Rate Limiting**
🔄 Rate Limiting: Limits the number of requests to third-party APIs to prevent

---

## 🐳 **Docker**
### **Build and Run Docker Image**
##### **1. Build the Docker Image:**
```bash
docker build -t employee-management .
```
##### **Run the Docker Container:**
```bash
docker run -p 8080:8080 employee-management
```


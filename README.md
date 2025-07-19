# MeeApp - Group and Event Management System

A comprehensive Spring Boot application for managing groups, events, posts, and messaging functionality.

## Features

### Group Management
- ✅ Create, edit, and delete groups
- ✅ Join and leave groups
- ✅ Public and private groups
- ✅ Group member management
- ✅ Group statistics (member count, message count, post count, event count)

### Event Management
- ✅ Create, edit, and delete events
- ✅ Join and leave events
- ✅ Public and private events
- ✅ Event participants management
- ✅ Event scheduling with location and max participants
- ✅ Event interests/tags
- ✅ Group-specific events

### Messaging System
- ✅ Send messages in groups
- ✅ Delete messages (author only)
- ✅ Message pagination
- ✅ Recent messages retrieval
- ✅ Message count by group

### Post System
- ✅ Create and delete posts in groups
- ✅ Post with images and interests
- ✅ Post pagination
- ✅ Posts by interest
- ✅ Post count by group

### User Management
- ✅ User authentication and authorization
- ✅ User profiles with interests
- ✅ User participation tracking

## Technology Stack

- **Backend**: Spring Boot 3.5.3
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA with Hibernate
- **Database Migration**: Liquibase
- **Security**: Spring Security with JWT
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Gradle
- **Java Version**: 21

## Getting Started

### Prerequisites

- Java 21
- PostgreSQL
- Gradle

### Database Setup

1. Create a PostgreSQL database named `meeapp`
2. Update the database configuration in `src/main/resources/application.yml` if needed

### Environment Variables

Create a `.env` file in the root directory:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=meeapp
DB_USERNAME=kratosgado
DB_PASSWORD=28935617
JWT_SECRET_KEY=your-secret-key-here
JWT_EXPIRATION_TIME=36000000
```

### Running the Application

1. **Start the application:**
   ```bash
   ./gradlew bootRun
   ```

2. **Build the application:**
   ```bash
   ./gradlew build
   ```

3. **Run tests:**
   ```bash
   ./gradlew test
   ```

4. **Database migration:**
   ```bash
   ./gradlew liquibaseUpdate
   ```

### Hot Reload Configuration

The application is configured for hot reload during development:

- **Spring DevTools**: Automatically restarts the application when Java files change
- **LiveReload**: Browser auto-refresh on port 35729
- **Excluded paths**: Static files and templates are excluded from restart

## API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:9000/docs/swagger-ui.html
- **OpenAPI JSON**: http://localhost:9000/docs/api-docs

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login user

### Groups
- `POST /api/groups` - Create a new group
- `PUT /api/groups/{groupId}` - Update a group
- `DELETE /api/groups/{groupId}` - Delete a group
- `POST /api/groups/{groupId}/join` - Join a group
- `POST /api/groups/{groupId}/leave` - Leave a group
- `GET /api/groups` - Get all groups
- `GET /api/groups/public` - Get public groups
- `GET /api/groups/my-groups` - Get my groups
- `GET /api/groups/{groupId}` - Get group by ID
- `GET /api/groups/user/{userId}` - Get groups by user

### Events
- `POST /api/events` - Create a new event
- `PUT /api/events/{eventId}` - Update an event
- `DELETE /api/events/{eventId}` - Delete an event
- `POST /api/events/{eventId}/join` - Join an event
- `POST /api/events/{eventId}/leave` - Leave an event
- `GET /api/events/{eventId}` - Get event by ID
- `GET /api/events/public/upcoming` - Get upcoming public events
- `GET /api/events/group/{groupId}` - Get events by group
- `GET /api/events/group/{groupId}/upcoming` - Get upcoming group events
- `GET /api/events/my-events` - Get my events
- `GET /api/events/created` - Get events I created

### Messages
- `POST /api/messages` - Send a message
- `DELETE /api/messages/{messageId}` - Delete a message
- `GET /api/messages/{messageId}` - Get message by ID
- `GET /api/messages/group/{groupId}` - Get messages by group
- `GET /api/messages/group/{groupId}/recent` - Get recent messages by group
- `GET /api/messages/my-messages` - Get my messages

### Posts
- `POST /api/posts` - Create a new post
- `DELETE /api/posts/{postId}` - Delete a post
- `GET /api/posts/{postId}` - Get post by ID
- `GET /api/posts/group/{groupId}` - Get posts by group
- `GET /api/posts/group/{groupId}/recent` - Get recent posts by group
- `GET /api/posts/my-posts` - Get my posts
- `GET /api/posts/interest/{interest}` - Get posts by interest

## Database Schema

### Core Tables
- `users` - User accounts and profiles
- `groups` - Group information
- `events` - Event information
- `messages` - Group messages
- `posts` - Group posts

### Junction Tables
- `user_groups` - Many-to-many relationship between users and groups
- `event_participants` - Many-to-many relationship between events and participants

### Collection Tables
- `user_interests` - User interests
- `post_interests` - Post interests
- `event_interests` - Event interests

## Development

### Project Structure
```
src/main/java/com/kratosgado/meeapp/
├── controllers/     # REST API controllers
├── services/        # Business logic services
├── repositories/    # Data access layer
├── models/          # JPA entities
├── dtos/           # Data transfer objects
├── security/       # Security configuration
└── utils/          # Utility classes
```

### Key Features Implemented

1. **Comprehensive CRUD Operations**: All entities support full CRUD operations
2. **Business Logic Validation**: Proper validation for group membership, event participation, etc.
3. **Pagination**: Support for paginated results in messages and posts
4. **Search and Filtering**: Events by date, posts by interest, etc.
5. **Security**: JWT-based authentication with proper authorization
6. **Database Migrations**: Liquibase for version-controlled database changes
7. **API Documentation**: Complete OpenAPI 3 documentation
8. **Hot Reload**: Development-friendly configuration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 
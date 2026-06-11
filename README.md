

Tiered Membership Backend

A Spring Boot backend application that powers a tiered club membership program. The system allows users to browse membership plans and tiers, purchase memberships, manage subscriptions, view benefits, and evaluate tier eligibility based on activity.


---

Features

View available membership plans

View available membership tiers

View purchasable membership offerings

Subscribe to a membership

View current membership details

Upgrade or downgrade membership tier

Cancel active membership

View tier benefits

Evaluate eligible tier based on user activity

Optimistic locking for membership updates



---

Tech Stack

Java 17

Spring Boot

Spring Web

Spring Data JPA

H2 Database

Maven



---

Project Structure

src/main/java/org/sample/tieredmembership

├── TieredmembershipApplication.java
└── membership
    ├── config
    ├── controller
    ├── dto
    ├── entity
    ├── enums
    ├── exception
    ├── repository
    └── service


---

Domain Model

MembershipOffering

A membership offering represents a purchasable combination of:

MembershipOffering = MembershipPlan + MembershipTier + Price

Examples:

Plan	Tier	Price

Monthly	Gold	₹299
Yearly	Platinum	₹3499


Core Entities

MembershipPlan

MembershipTier

MembershipOffering

UserMembership

TierBenefit

TierEligibilityRule

MembershipEvent


Relationships

UserMembership
      |
      v
MembershipOffering
   /         \
  v           v
Plan        Tier


---

Seed Data

The application loads demo data on startup from:

membership/config/DemoDataLoader.java

Membership Plans

Monthly

Quarterly

Yearly


Membership Tiers

Silver

Gold

Platinum


Membership Offerings

Monthly Silver

Monthly Gold

Monthly Platinum

Quarterly Silver

Quarterly Gold

Quarterly Platinum

Yearly Silver

Yearly Gold

Yearly Platinum


Benefits

Free delivery

Discount percentage


Eligibility Rules

Gold

Eligible if any of the following is true:

Monthly order count ≥ 5

Monthly order value ≥ ₹5,000


Platinum

Eligible if any of the following is true:

Monthly order count ≥ 10

Monthly order value ≥ ₹15,000

User cohort = VIP



---

Running the Application

Prerequisites

Java 17

Maven

IntelliJ IDEA (or any Java IDE)


Start the Application

Import the project into your IDE and run:

TieredmembershipApplication.java

The application will start on:

http://localhost:8081


---

H2 Database Console

URL:

http://localhost:8081/h2-console

Credentials:

JDBC URL : jdbc:h2:mem:tieredmembership
Username : sa
Password : <empty>


---

API Reference

Base URL:

http://localhost:8081


---

1. Get Membership Plans

GET /api/membership/plans

Example:

curl http://localhost:8081/api/membership/plans


---

2. Get Membership Tiers

GET /api/membership/tiers

Example:

curl http://localhost:8081/api/membership/tiers


---

3. Get Membership Offerings

GET /api/membership/offerings

Example:

curl http://localhost:8081/api/membership/offerings


---

4. Subscribe to Membership

POST /api/users/{userId}/membership/subscriptions

Request:

{
  "offeringId": 2
}

Example:

curl -X POST http://localhost:8081/api/users/101/membership/subscriptions \
-H 'Content-Type: application/json' \
-d '{"offeringId":2}'


---

5. Get Current Membership

GET /api/users/{userId}/membership/current

Example:

curl http://localhost:8081/api/users/101/membership/current


---

6. Upgrade / Downgrade Membership Tier

PATCH /api/users/{userId}/membership/tier

Request:

{
  "offeringId": 3
}

Example:

curl -X PATCH http://localhost:8081/api/users/101/membership/tier \
-H 'Content-Type: application/json' \
-d '{"offeringId":3}'

> Tier changes are performed by switching to another offering.




---

7. Get Membership Benefits

GET /api/users/{userId}/membership/benefits

Example:

curl http://localhost:8081/api/users/101/membership/benefits


---

8. Evaluate Eligible Tier

POST /api/users/{userId}/membership/tier/evaluate

Request:

{
  "monthlyOrderCount": 12,
  "monthlyOrderValue": 9000,
  "cohort": "REGULAR"
}

Example:

curl -X POST http://localhost:8081/api/users/101/membership/tier/evaluate \
-H 'Content-Type: application/json' \
-d '{"monthlyOrderCount":12,"monthlyOrderValue":9000,"cohort":"REGULAR"}'


---

9. Cancel Membership

DELETE /api/users/{userId}/membership/current

Example:

curl -X DELETE http://localhost:8081/api/users/101/membership/current


---

Functional Requirement Mapping

Requirement	Implementation

View membership plans	GET /api/membership/plans
View membership tiers	GET /api/membership/tiers
Select plan and tier	MembershipOffering
Subscribe to membership	POST /api/users/{userId}/membership/subscriptions
Upgrade/Downgrade tier	PATCH /api/users/{userId}/membership/tier
Cancel membership	DELETE /api/users/{userId}/membership/current
View current membership	GET /api/users/{userId}/membership/current
Track membership expiry	UserMembership.endDate
Tier eligibility criteria	TierEligibilityRule
Membership benefits	TierBenefit



---

Concurrency Handling

Basic concurrency handling is included:

Service-layer operations use @Transactional

UserMembership uses optimistic locking via @Version

Validation prevents multiple active memberships for the same user


Not Implemented

Idempotency keys

Distributed locking

Database-level unique constraints for active memberships

Multi-node concurrency guarantees



---

Assumptions & Simplifications

This project is intentionally simplified for demonstration purposes and does not include:

Payment gateway integration

User management service

Order management service

Checkout/cart calculations

Membership renewal workflows

Scheduled expiry jobs

Administrative APIs

Production database migrations

Authentication and authorization

External storage systems


The application uses an in-memory H2 database for easy local development and testing.

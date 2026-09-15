Markdown
# Automation Exercise - E-Commerce Test Automation Framework

This repository contains an end-to-end automated UI and API testing framework built for the [Automation Exercise](https://www.automationexercise.com/) e-commerce platform. The suite executes automated regression tests, validates functional user flows, and captures failure diagnostics.

---

## 📌 Project Overview

* **Target Application:** [Automation Exercise](https://www.automationexercise.com/)
* **Tested By:** Vijayasree J S
* **GitHub Repository:** [Entri-final-project-Automation-Exercise](https://github.com/Vijayasreejs/Entri-final-project-Automation-Exercise.git)
* **Design Architecture:** Page Object Model (POM)
* **Target OS & Browser:** Windows 11 Pro | Google Chrome (v152.0.7977.84)

---

## 🛠️ Technology Stack

* **Programming Language:** Java
* **Automation Tool:** Selenium WebDriver
* **Test Runner Framework:** TestNG
* **Build Automation:** Apache Maven
* **Data-Driven Utility:** Apache POI (Excel integration via `@DataProvider`)
* **Reporting Utilities:** TestNG HTML Reports & Custom Screenshot Listener (`ScreenshotList.class`)

---

## 🧪 Testing Types Performed

* **Automated UI Regression Testing:** Execution of automated end-to-end UI flows using Selenium WebDriver.
* **Functional Testing:** Verification of input validations, button actions, and UI navigation workflows.
* **Negative Testing:** Testing boundary conditions, missing inputs, and invalid checkout/payment inputs.
* **Integration Testing:** Data pass-through verification across core flows (`Product Page -> Cart -> Checkout -> Payment`).
* **Exploratory Testing:** Dynamic, unscripted exploration to identify unexpected edge cases and UI defects.

---

## 📋 Scope of Testing

### In-Scope
* **User Onboarding:** Registration, login, logout, and account deletion workflows.
* **Catalog & Search:** Category navigation, product filtering, and product detail verification.
* **Cart & Checkout:** Adding/removing items, quantity updates, and checkout flow.
* **Payment Processing:** Payment UI inputs, order generation, and invoice download.

### Out-of-Scope
* Live third-party payment gateway API authorizations (e.g., live PayPal/Stripe).
* Non-functional load/performance testing and security penetration testing.

---

## 📊 Executive Summary Metrics

| Metric | Value |
| :--- | :--- |
| **Total Test Cases Designed** | 346 |
| **Test Cases Executed** | 346 (100%) |
| **Passed Test Cases** | 256 (74.0%) |
| **Failed Test Cases** | 90 (26.0%) |
| **Total Defects Identified** | 91 |
| **Defect Density** | ~2.6 defects per 10 test cases |

### Defect Severity Distribution
* **Critical:** 13 (14.3%)
* **Major:** 51 (56.0%)
* **Minor:** 27 (29.7%)

---

## 🧩 Module-Wise Performance Breakdown

| Module | Designed | Passed | Failed | Pass Rate (%) | Logged Defects |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **Home** | 44 | 39 | 5 | 88.6% | 6 |
| **Login** | 45 | 41 | 4 | 91.1% | 4 |
| **New User Registration** | 56 | 45 | 11 | 80.4% | 11 |
| **Products** | 39 | 26 | 13 | 66.7% | 13 |
| **Cart** | 27 | 20 | 7 | 74.1% | 7 |
| **Checkout** | 21 | 15 | 6 | 71.4% | 6 |
| **Payment** | 27 | 11 | 16 | 40.7% | 16 |
| **Test Cases** | 22 | 18 | 4 | 81.8% | 4 |
| **API Testing** | 19 | 8 | 11 | 42.1% | 11 |
| **Video Tutorials** | 25 | 22 | 3 | 88.0% | 3 |
| **Contact Us** | 21 | 11 | 10 | 52.4% | 10 |
| **TOTAL** | **346** | **256** | **90** | **74.0%** | **91** |

---

## 🚀 Execution & Setup Instructions

### Prerequisites
* Java JDK 11 or higher
* Apache Maven
* Google Chrome Browser

### Running the Suite

1. **Clone the repository:**
   ```bash

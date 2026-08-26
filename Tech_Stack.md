Next is **`TECH_STACK.md`**. This defines the technology choices and, importantly, the boundaries around them so the project doesn't accumulate random libraries during development.

````markdown
# RE:PLACE — Technology Stack

## 1. Stack Philosophy

The stack should prioritize:

- Android-native performance
- AI/ML capability
- AR capability
- Offline-first functionality
- Replaceable AI models
- Production maintainability
- Low unnecessary dependency count
- Open-source technologies where practical

Do not introduce a technology only because it is popular or convenient.

---

# 2. Android Application

## Core

| Technology | Purpose |
|---|---|
| Kotlin | Primary Android language |
| Jetpack Compose | UI |
| Android SDK | Platform APIs |
| Gradle + Kotlin DSL | Build system |

## Android Libraries

| Technology | Purpose |
|---|---|
| CameraX | Camera capture and preview |
| ARCore | Augmented reality |
| Media3 | Media playback |
| Android Media APIs | Media handling |
| Room | Local structured persistence |
| DataStore | Lightweight preferences |
| WorkManager | Long-running/background work |
| Coroutines | Asynchronous processing |
| Kotlin Serialization | Data serialization |

---

# 3. Android Architecture

Use:

```text
Kotlin
   ↓
Jetpack Compose
   ↓
MVVM / Unidirectional Data Flow
   ↓
Domain / Use Cases
   ↓
Repositories
   ↓
Local / Remote Data Sources
````

Recommended architectural principles:

* feature-based organization
* single source of truth for UI state
* dependency injection
* repository abstraction
* asynchronous processing
* lifecycle-aware components

---

# 4. Dependency Injection

### Preferred

**Hilt**

Use dependency injection for:

* repositories
* AI engines
* network clients
* storage
* processing services
* configuration

The UI should not manually construct infrastructure dependencies.

---

# 5. AI / Computer Vision

## Development

| Technology | Purpose                      |
| ---------- | ---------------------------- |
| Python     | AI experimentation           |
| PyTorch    | Model development/evaluation |
| OpenCV     | Image/video processing       |
| NumPy      | Numerical processing         |

## Mobile Inference

Prefer:

```text
Model
 ↓
ONNX / LiteRT-compatible representation
 ↓
Android Runtime
```

The exact runtime should be selected after benchmarking the target models on real Android hardware.

Possible runtimes:

* ONNX Runtime Mobile
* LiteRT

Do not commit to a runtime purely based on theoretical benchmark results.

---

# 6. AI Capabilities

The system may eventually require:

```text
AI
├── Person Segmentation
├── Object Segmentation
├── Mask Refinement
├── Video Tracking
├── Temporal Consistency
└── Optional Depth / Pose
```

The AI layer must expose interfaces rather than hard-code model implementations.

Example:

```text
SegmentationEngine
        │
        ├── LocalSegmentationEngine
        │
        └── CloudSegmentationEngine
```

---

# 7. AR

### Primary Technology

**Google ARCore**

Responsibilities:

* camera pose
* plane detection
* anchors
* spatial tracking
* environment understanding
* depth where supported

AR functionality must remain isolated from general UI and AI logic.

---

# 8. Rendering & Media

Use Android-native/media-focused technologies where possible.

Responsibilities:

```text
Rendering
├── Image composition
├── AR composition
├── Video frame processing
├── Effects
└── Export
```

Potential technologies:

* Android graphics APIs
* OpenGL ES / GPU rendering where required
* Media3
* Android media codecs
* hardware-accelerated processing

The rendering implementation should be benchmarked before introducing a large third-party editing framework.

---

# 9. Local Storage

## Room

Use for structured application data:

```text
Subjects
Projects
Scenes
Settings
Processing Metadata
```

## File Storage

Use Android-supported application/media storage for:

* source images
* source videos
* transparent subject assets
* rendered outputs
* temporary processing files

Avoid storing large binary media directly inside Room.

---

# 10. Backend

The backend is optional for the core offline experience.

### Backend

**Python + FastAPI**

Responsibilities:

* cloud inference API
* job management
* processing status
* optional authentication
* optional cloud storage integration

Architecture:

```text
Android
   ↓
FastAPI
   ↓
Inference Job
   ↓
GPU Worker
   ↓
Result
```

---

# 11. Cloud AI

Cloud inference should be treated as a separate implementation of the same AI interface used by local inference.

```text
                 AI Interface
                      │
             ┌────────┴────────┐
             ↓                 ↓
        Local Engine       Cloud Engine
             │                 │
          Device             API
```

This allows cloud AI to be added or removed without changing the product layer.

---

# 12. Backend Database

### PostgreSQL

Use for server-side structured metadata if cloud features require persistence.

Potential entities:

```text
User
InferenceJob
ProcessingResult
ProjectMetadata
```

Do not make PostgreSQL a requirement for local-only operation.

---

# 13. Object Storage

If cloud processing is introduced, large media should not be stored directly inside PostgreSQL.

Use object storage for:

* uploaded media
* temporary processing files
* processed results

The exact provider can be selected later based on:

* cost
* student/research availability
* region
* storage limits
* deployment requirements

---

# 14. API

### REST

Use REST for the initial cloud API.

Example:

```text
POST   /api/v1/inference
GET    /api/v1/jobs/{job_id}
GET    /api/v1/results/{result_id}
DELETE /api/v1/jobs/{job_id}
```

The Android application should communicate through a repository/API layer rather than calling endpoints directly from UI code.

---

# 15. Serialization

Use a consistent schema between Android and backend.

Recommended:

**JSON for metadata/control**

Binary media should use appropriate upload/download mechanisms rather than embedding large files inside JSON.

---

# 16. Networking

Use a standard Android HTTP client with:

* connection timeout
* read timeout
* retry policy
* cancellation
* network state handling
* secure HTTPS communication

The networking layer must support:

```text
ONLINE
OFFLINE
CONNECTING
TIMEOUT
SERVER_ERROR
```

---

# 17. Offline/Online Decision Layer

The application should expose a unified interface:

```text
InferenceEngine
```

with implementations:

```text
LocalInferenceEngine
CloudInferenceEngine
AdaptiveInferenceEngine
```

The adaptive engine decides which implementation to use.

Factors may include:

```text
Device Capability
Network
Media Size
Media Duration
Model Complexity
Expected Quality
Latency
Battery
```

---

# 18. Testing

## Android

Use:

* JUnit
* AndroidX testing
* Compose UI testing

## Backend

Use:

* Pytest
* FastAPI test utilities

## AI

Use:

* Python evaluation scripts
* benchmark datasets
* model-specific metrics

## Performance

Measure:

* inference latency
* FPS
* RAM
* CPU
* GPU where measurable
* battery impact
* export time
* network usage

---

# 19. Development Tools

Recommended:

```text
IDE
→ Android Studio

AI Development
→ Python environment

Version Control
→ Git + GitHub

API Testing
→ Postman / equivalent

Containerization
→ Docker

CI
→ GitHub Actions
```

Use whichever equivalent tool is already available in the development environment rather than adding unnecessary tooling.

---

# 20. Code Quality

Recommended:

### Android

* Kotlin formatting
* Android lint
* static analysis
* unit tests

### Python

* Ruff
* Pytest
* type hints

### Git

Use small, meaningful commits.

Example:

```text
feat: add subject asset repository
fix: handle failed segmentation
perf: reduce image preprocessing latency
test: add subject repository tests
docs: update architecture
```

---

# 21. Environment Management

Never hard-code:

* API keys
* cloud credentials
* private URLs
* production secrets

Use environment-specific configuration.

```text
Development
Staging
Production
```

Android must receive only the configuration it actually requires.

---

# 22. Deployment

## Android

```text
Debug
  ↓
Internal Testing
  ↓
Release Candidate
  ↓
Production
```

## Backend

```text
Docker
  ↓
Backend Service
  ↓
Inference Worker
  ↓
GPU/Compute Environment
```

The cloud backend must remain optional for the MVP.

---

# 23. Technology Selection Rules

Before adding a dependency, ask:

1. Is it actually necessary?
2. Does Android/platform functionality already provide it?
3. Does it introduce significant APK size?
4. Does it affect performance?
5. Is it actively maintained?
6. Does it complicate offline functionality?
7. Can the same functionality be implemented cleanly with the current stack?

If the answer is unclear, do not add it immediately.

---

# 24. Initial Locked Stack

For the first implementation, use:

```text
ANDROID
Kotlin
Jetpack Compose
CameraX
ARCore
Room
DataStore
Coroutines
Hilt
Media3

AI
Python
PyTorch
OpenCV
ONNX / LiteRT

BACKEND
Python
FastAPI
PostgreSQL

INFRASTRUCTURE
Docker
GitHub Actions

VERSION CONTROL
Git
GitHub
```

Model choice, inference runtime, rendering implementation, and cloud provider should be **validated through prototypes/benchmarks rather than assumed upfront**.

---

# 25. Stack Evolution Rule

The technology stack is not considered permanently locked.

A change is justified when it provides a measurable improvement in:

* performance
* reliability
* maintainability
* device compatibility
* AI quality
* development efficiency

Any significant stack change must be documented in `ARCHITECTURE.md` and this file.

```
```

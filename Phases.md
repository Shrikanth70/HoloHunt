# RE:PLACE — Development Phases & Progress Tracker

> **Rule:** A phase is complete only when its acceptance criteria are satisfied and the implementation has been tested.

---

# Project Status

```text
Current Phase: Phase 2
Overall Progress: 20%
Status: IN PROGRESS
```

### Current Focus

```text
Working On:
- Phase 2: AI Subject Extraction

Next:
- Select initial segmentation approach
- Create inference interface
- Implement local inference

Blockers:
- None

Last Completed:
- Phase 1: Media Foundation (CameraX, VideoCapture, Gallery picker, Validation, Metadata, Preview flow)
```

---

# Phase 0 — Project Foundation

## Goal

Establish the production-ready project structure and development workflow.

### Tasks

* [x] Create Git repository
* [x] Create Android project
* [x] Establish package/module structure
* [x] Configure Kotlin
* [x] Configure Jetpack Compose
* [x] Configure dependency management
* [x] Configure lint/static analysis
* [x] Configure debug/release environments
* [x] Create basic CI workflow
* [x] Add project documentation
* [x] Create development branch strategy

### Acceptance Criteria

* Android project builds successfully.
* Application launches on a physical Android device.
* Project structure follows `ARCHITECTURE.md`.
* Debug build works.
* Release configuration exists.
* CI can successfully build the project.

### Status

```text
COMPLETE ✅
```

---

# Phase 1 — Media Foundation

## Goal

Build reliable camera and gallery media acquisition.

### Tasks

* [x] Camera permission handling
* [x] Camera preview
* [x] Image capture
* [x] Video capture foundation
* [x] Gallery image picker
* [x] Gallery video picker
* [x] Media validation
* [x] Media metadata extraction
* [x] Local temporary media management
* [x] Error handling

### Acceptance Criteria

A user can:

```text
Open Camera
     ↓
Capture Image
     ↓
Preview
     ↓
Accept / Retake
```

and:

```text
Open Gallery
     ↓
Select Image / Video
     ↓
Preview
     ↓
Continue
```

### Status

```text
COMPLETE ✅
```

---

# Phase 2 — AI Subject Extraction

## Goal

Create the first working AI pipeline for extracting people and objects.

> **This is the first major technical milestone.**

### Tasks

* [ ] Select initial segmentation approach
* [ ] Create inference interface
* [ ] Implement local inference
* [ ] Image preprocessing
* [ ] Segmentation
* [ ] Mask generation
* [ ] Mask refinement
* [ ] Transparent subject generation
* [ ] Subject preview
* [ ] Measure inference latency
* [ ] Measure memory usage
* [ ] Handle inference failure

### Acceptance Criteria

Given a supported image:

```text
Input Image
     ↓
AI Processing
     ↓
Person/Object Mask
     ↓
Transparent Subject
```

The resulting subject should be visually usable in the application.

### Important Validation

Test:

* different backgrounds
* multiple people
* common objects
* low-light images
* complex edges
* different image resolutions

### Status

```text
NOT STARTED
```

---

# Phase 3 — Subject Asset System

## Goal

Turn extracted subjects into reusable application assets.

### Tasks

* [ ] Define `SubjectAsset`
* [ ] Subject metadata
* [ ] Transparent asset storage
* [ ] Subject preview
* [ ] Save subject
* [ ] Delete subject
* [ ] Subject library
* [ ] Load subject
* [ ] Cache processing results

### Acceptance Criteria

A user can:

```text
Extract Subject
      ↓
Save
      ↓
Close App
      ↓
Reopen App
      ↓
Find Subject
      ↓
Reuse Subject
```

### Status

```text
NOT STARTED
```

---

# Phase 4 — AR Foundation

## Goal

Place extracted subjects into the real world.

### Tasks

* [ ] AR session
* [ ] Camera integration
* [ ] Plane detection
* [ ] Surface selection
* [ ] AR anchor creation
* [ ] Subject placement
* [ ] Move gesture
* [ ] Scale gesture
* [ ] Rotation gesture
* [ ] Delete subject
* [ ] Duplicate subject
* [ ] AR session lifecycle
* [ ] Unsupported-device handling

### Acceptance Criteria

A user can:

```text
Open AR
   ↓
Detect Surface
   ↓
Select Surface
   ↓
Place Subject
   ↓
Move / Scale / Rotate
```

The subject must remain anchored while the camera moves.

### Status

```text
NOT STARTED
```

---

# Phase 5 — Creative Canvas

## Goal

Allow users to create compositions without AR.

### Tasks

* [ ] Canvas foundation
* [ ] Image background
* [ ] Video background foundation
* [ ] Add subject
* [ ] Move subject
* [ ] Scale subject
* [ ] Rotate subject
* [ ] Layer ordering
* [ ] Delete subject
* [ ] Duplicate subject
* [ ] Scene state management

### Acceptance Criteria

A user can:

```text
Select Background
       ↓
Add Subject
       ↓
Transform Subject
       ↓
Create Composition
       ↓
Preview
```

### Status

```text
NOT STARTED
```

---

# Phase 6 — Effects & Entertainment

## Goal

Make the application entertaining rather than simply functional.

### Initial Effects

* [ ] Clone
* [ ] Mirror
* [ ] Giant
* [ ] Tiny
* [ ] Floating
* [ ] Basic animation
* [ ] Basic visual filters

### Tasks

* [ ] Effects architecture
* [ ] Effect interface
* [ ] Effect parameter system
* [ ] Effect preview
* [ ] Effect undo/redo foundation

### Acceptance Criteria

New effects can be added without rewriting the core editor or AR system.

### Status

```text
NOT STARTED
```

---

# Phase 7 — Video Processing & Recording

## Goal

Allow users to record and export creations.

### Tasks

* [ ] AR recording
* [ ] Canvas recording
* [ ] Frame composition
* [ ] Audio handling
* [ ] Video encoding
* [ ] Progress indicator
* [ ] Cancel processing
* [ ] Export to device
* [ ] MediaStore integration
* [ ] Export error handling

### Acceptance Criteria

The user can:

```text
Create Scene
    ↓
Record
    ↓
Process
    ↓
Export
    ↓
Find Video in Gallery
```

The exported video must be playable using standard Android media players.

### Status

```text
NOT STARTED
```

---

# Phase 8 — Video Subject Extraction

## Goal

Extend AI processing from images to videos.

> **This is one of the highest-risk phases.**

### Tasks

* [ ] Video decoding
* [ ] Frame sampling
* [ ] Initial segmentation
* [ ] Temporal tracking
* [ ] Mask propagation
* [ ] Mask refinement
* [ ] Temporal consistency
* [ ] Subject reconstruction
* [ ] Video asset storage
* [ ] Processing progress
* [ ] Cancellation/resume strategy

### Acceptance Criteria

Given a supported video:

```text
Video
 ↓
Subject Extraction
 ↓
Tracked Subject
 ↓
Creative / AR Scene
```

The extracted subject should remain reasonably stable across frames.

### Quality Checks

Evaluate:

* flickering
* mask stability
* edge quality
* subject disappearance
* tracking failures
* processing time

### Status

```text
NOT STARTED
```

---

# Phase 9 — Hybrid Offline/Online AI

## Goal

Introduce intelligent routing between local and cloud inference.

### Tasks

* [ ] Define inference request
* [ ] Define inference result
* [ ] Local inference implementation
* [ ] Backend inference API
* [ ] Cloud inference implementation
* [ ] Network detection
* [ ] Device capability assessment
* [ ] Media complexity estimation
* [ ] Inference router
* [ ] Retry/fallback strategy
* [ ] Cloud processing status
* [ ] Privacy controls

### Routing

```text
AI Request
    ↓
Capability Assessment
    ↓
┌───────────────┬────────────────┐
│ Local Suitable│ Cloud Suitable │
↓               ↓
LOCAL           CLOUD
        \       /
         \     /
          RESULT
```

### Acceptance Criteria

The same application feature must work without requiring the UI to know whether processing occurred:

* locally
* remotely

### Status

```text
NOT STARTED
```

---

# Phase 10 — Production Hardening

## Goal

Move from a functional project to a production-quality application.

### Performance

* [ ] Memory profiling
* [ ] CPU profiling
* [ ] GPU profiling
* [ ] AI latency optimization
* [ ] Rendering optimization
* [ ] Battery testing
* [ ] Large-media testing
* [ ] Startup optimization

### Reliability

* [ ] Crash handling
* [ ] Processing recovery
* [ ] Storage failure handling
* [ ] Network failure handling
* [ ] AR failure handling
* [ ] AI failure handling

### Security

* [ ] Secure API communication
* [ ] Secret management
* [ ] Media privacy review
* [ ] Permission review
* [ ] Temporary file cleanup

### UX

* [ ] Loading states
* [ ] Empty states
* [ ] Error states
* [ ] Onboarding
* [ ] Accessibility review
* [ ] Gesture refinement
* [ ] Animation polish

### Status

```text
NOT STARTED
```

---

# Phase 11 — Testing & Release

## Goal

Validate the application on real devices and prepare a release build.

### Testing

* [ ] Unit tests
* [ ] ViewModel tests
* [ ] AI pipeline tests
* [ ] Repository tests
* [ ] AR tests
* [ ] Media tests
* [ ] Export tests
* [ ] Integration tests
* [ ] Offline tests
* [ ] Network failure tests

### Device Testing

Test across:

* [ ] Low-end device
* [ ] Mid-range device
* [ ] High-end device
* [ ] Different Android versions
* [ ] AR-supported device
* [ ] AR-unsupported device

### Release

* [ ] Release build
* [ ] App icon
* [ ] Splash screen
* [ ] App metadata
* [ ] Privacy policy
* [ ] Permission descriptions
* [ ] Release signing
* [ ] Final QA

### Status

```text
NOT STARTED
```

---

# Phase 12 — Research & Evaluation

## Goal

Convert the engineering work into measurable AIML/research results.

### AI Evaluation

* [ ] Segmentation quality
* [ ] IoU
* [ ] Dice score
* [ ] Boundary quality
* [ ] Temporal consistency

### System Evaluation

* [ ] Local inference latency
* [ ] Cloud inference latency
* [ ] Memory usage
* [ ] CPU/GPU usage
* [ ] Battery impact
* [ ] Network usage

### Hybrid Inference Evaluation

Compare:

```text
Local
vs
Cloud
vs
Adaptive
```

Measure:

* latency
* quality
* resource consumption
* reliability

### User Evaluation

Potential measurements:

* task completion time
* perceived output quality
* usability
* entertainment value

### Status

```text
NOT STARTED
```

---

# Progress Summary

| Phase               | Status | Progress |
| ------------------- | ------ | -------: |
| 0. Foundation       | ⬜      |       0% |
| 1. Media            | ⬜      |       0% |
| 2. AI Extraction    | ⬜      |       0% |
| 3. Subject Assets   | ⬜      |       0% |
| 4. AR               | ⬜      |       0% |
| 5. Canvas           | ⬜      |       0% |
| 6. Effects          | ⬜      |       0% |
| 7. Video            | ⬜      |       0% |
| 8. Video AI         | ⬜      |       0% |
| 9. Hybrid AI        | ⬜      |       0% |
| 10. Production      | ⬜      |       0% |
| 11. Testing/Release | ⬜      |       0% |
| 12. Research        | ⬜      |       0% |

---

# Status Legend

```text
⬜ NOT STARTED
🟡 IN PROGRESS
🔵 BLOCKED
🟢 COMPLETE
🔴 NEEDS REWORK
```

---

# Phase Completion Rule

A phase must **not** be marked complete simply because the code exists.

A phase is complete only when:

```text
Implementation
      +
Testing
      +
Acceptance Criteria
      +
Documentation
      ↓
PHASE COMPLETE
```

---

# Current Development Rule

Always work on the **smallest meaningful increment** of the current phase.

Do not start a later phase merely because it looks more interesting.

If a phase exposes a major architectural problem:

```text
Stop
 ↓
Document problem
 ↓
Update Architecture
 ↓
Resolve / Prototype
 ↓
Continue
```

---

# Current Project State

```text
Current Phase: Phase 0 — Project Foundation

Completed:
-

In Progress:
-

Blocked:
-

Next Task:
-

Last Updated:
-
```

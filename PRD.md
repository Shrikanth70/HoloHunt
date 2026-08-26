# PRD — AI-Powered AR Entertainment App

## 1. Product Overview

### Working Name

**RE:PLACE**

### Product Concept

RE:PLACE is an Android entertainment application that allows users to extract people or objects from images and videos using AI, then place, manipulate, and animate those subjects inside:

1. **Live AR environments** using the phone camera.
2. **Creative image/video canvases** for editing and content creation.

The application supports both **offline/on-device AI processing** and **online/cloud-assisted processing**, with the system automatically selecting the appropriate processing path.

### Core Idea

> **Capture anything. Cut it out. Put it anywhere. Create something fun.**

---

# 2. Problem Statement

Most mobile applications provide either:

* basic photo background removal,
* conventional video editing,
* or AR object placement.

These experiences are generally separated.

Users have no simple entertainment-focused workflow that lets them take a person or object from their own media and seamlessly reuse it across both real-world AR scenes and creative media.

RE:PLACE combines these capabilities into one workflow:

```text
Capture / Import
       ↓
AI Subject Extraction
       ↓
Reusable Subject
       ↓
┌──────────────┬──────────────┐
│              │              │
AR             Creative       │
World          Canvas         │
│              │              │
└──────────────┴──────────────┘
       ↓
Create
       ↓
Record / Save / Share
```

---

# 3. Target Users

### Primary Users

Young smartphone users interested in:

* entertainment
* AR experiences
* memes
* short-form video
* creative photography
* experimenting with friends
* social media content creation

### Secondary Users

Students and creators who want to quickly create:

* humorous videos
* visual effects
* creative edits
* AR content
* short-form social media content

---

# 4. Product Goals

## Primary Goals

1. Make AI-powered subject extraction simple.
2. Allow users to reuse extracted subjects.
3. Enable intuitive AR placement.
4. Enable creative canvas-based editing.
5. Allow users to record and save creations.
6. Support offline functionality for core capabilities.
7. Use cloud processing when higher processing capability is required.
8. Provide a polished consumer application rather than a technical AI demo.

## Engineering Goals

1. Modular Android architecture.
2. Replaceable AI models.
3. Clear separation between AI, AR, rendering, storage, and UI.
4. Reliable media processing.
5. Efficient mobile inference.
6. Production-quality error handling.
7. Measurable performance.
8. Maintainable and testable code.

---

# 5. Core User Journey

```text
Open App
   ↓
Create
   ↓
Capture / Import
   ↓
Select Person / Object
   ↓
AI Processing
   ↓
Subject Created
   ↓
Choose:
   ├── AR
   └── Canvas
   ↓
Position / Scale / Rotate
   ↓
Add Effects
   ↓
Preview
   ↓
Record / Export
   ↓
Save / Share
```

The user should not need to understand whether AI processing happens locally or in the cloud.

---

# 6. Core Features

## 6.1 Camera Capture

Users can:

* open the device camera,
* capture an image,
* capture video,
* use the captured media for subject extraction.

---

## 6.2 Media Import

Users can import:

* images,
* videos,

from the device gallery.

---

## 6.3 AI Subject Extraction

The application should identify and extract:

* people,
* common objects.

The output should be a reusable subject with transparency/mask information.

Example:

```text
Original Image
      ↓
AI Segmentation
      ↓
Subject Mask
      ↓
Transparent Subject
```

---

## 6.4 Subject Library

Extracted subjects can be saved locally and reused.

Example:

```text
My Subjects

[ Me ]
[ Friend ]
[ Dog ]
[ Guitar ]
[ Car ]
```

Each subject should retain the information required for reuse in future creations.

---

## 6.5 AR Mode

Users can place extracted subjects into the live camera environment.

Core interactions:

* move
* scale
* rotate
* reposition
* delete
* duplicate

The system should support spatial anchoring and camera movement.

---

## 6.6 Creative Canvas Mode

Users can place extracted subjects on:

* images,
* videos,
* creative backgrounds.

Core interactions:

* move
* scale
* rotate
* duplicate
* reorder
* remove

---

## 6.7 Effects

The initial effects system should focus on simple, entertaining interactions.

Potential effects:

* duplicate
* mirror
* giant
* tiny
* floating
* clone
* basic animation
* filters

Effects should be modular so additional effects can be introduced later.

---

## 6.8 Video Recording

Users can record their AR or creative composition.

The application should:

* capture the final composition,
* preserve audio where applicable,
* generate a standard video file,
* save the result to device storage.

---

## 6.9 Export and Sharing

Users can:

* save creations,
* export videos,
* save screenshots,
* invoke the Android share interface.

---

# 7. Offline Mode

Core functionality should remain available without an internet connection where device capabilities permit it.

Offline capabilities should include:

* camera
* media import
* basic subject extraction
* subject library
* AR placement
* creative canvas
* basic effects
* local project storage
* recording
* export

The application must not require account creation or cloud connectivity for the core creation experience.

---

# 8. Online Mode

Cloud processing may be used for tasks that exceed practical on-device capabilities.

Potential cloud capabilities include:

* advanced segmentation,
* complex video processing,
* higher-quality processing,
* longer media processing,
* computationally expensive models.

The user experience should remain consistent regardless of processing location.

---

# 9. Adaptive Processing

The application should eventually contain an inference decision layer.

```text
             AI REQUEST
                  ↓
       Capability Assessment
                  ↓
      ┌───────────┴───────────┐
      ↓                       ↓
 Local Suitable?         Cloud Required?
      ↓                       ↓
 Local AI                Cloud AI
      └───────────┬───────────┘
                  ↓
            Subject Result
```

The decision may consider:

* network availability,
* device capability,
* media size,
* media duration,
* processing complexity,
* expected quality,
* latency,
* battery constraints.

This is a key technical/research component of the project.

---

# 10. Functional Requirements

### FR-01 — Media Capture

The system shall allow users to capture media through the device camera.

### FR-02 — Media Import

The system shall allow users to import supported images and videos from device storage.

### FR-03 — Subject Extraction

The system shall extract supported people and objects from user media.

### FR-04 — Subject Persistence

The system shall allow extracted subjects to be saved and reused.

### FR-05 — AR Placement

The system shall allow subjects to be placed within supported AR environments.

### FR-06 — Canvas Placement

The system shall allow subjects to be placed within creative image/video scenes.

### FR-07 — Transformation

Users shall be able to move, scale, and rotate subjects.

### FR-08 — Effects

The system shall support modular creative effects.

### FR-09 — Recording

The system shall allow users to record their creation.

### FR-10 — Export

The system shall export supported creations to device storage.

### FR-11 — Offline Processing

The system shall support core functionality without network connectivity where technically feasible.

### FR-12 — Cloud Processing

The system may route computationally expensive processing to a backend when online processing is appropriate.

---

# 11. Non-Functional Requirements

## Performance

The application should:

* minimize UI latency,
* avoid unnecessary memory consumption,
* process media asynchronously,
* avoid blocking the main UI thread,
* provide processing progress for long-running operations.

## Reliability

The application should:

* gracefully handle failed inference,
* handle unsupported media,
* recover from interrupted processing,
* avoid corrupting saved projects.

## Usability

The application should:

* require minimal technical knowledge,
* provide clear visual feedback,
* use intuitive gestures,
* minimize unnecessary configuration.

## Maintainability

The codebase should:

* use modular architecture,
* separate concerns,
* expose clear interfaces,
* allow AI models to be replaced independently.

## Privacy

User media should remain local by default.

Media should only be uploaded to cloud services when required by the selected processing path and with appropriate user awareness.

---

# 12. MVP Scope

The first production-oriented MVP should contain:

### Media

* [ ] Camera image capture
* [ ] Gallery image import

### AI

* [ ] Person segmentation
* [ ] Basic object segmentation
* [ ] Transparent subject generation

### Subject Management

* [ ] Subject preview
* [ ] Save subject
* [ ] Subject library

### AR

* [ ] Plane detection
* [ ] Subject placement
* [ ] Move
* [ ] Scale
* [ ] Rotate

### Canvas

* [ ] Background image
* [ ] Subject placement
* [ ] Transform controls

### Output

* [ ] Screenshot
* [ ] Short video recording
* [ ] Save to device

### Offline

* [ ] Local inference for supported image processing
* [ ] Local subject storage
* [ ] Local project storage

---

# 13. Post-MVP Features

These should not block the MVP.

### Advanced AI

* video segmentation
* temporal tracking
* depth-aware segmentation
* improved edge refinement
* pose-aware effects

### Advanced AR

* realistic occlusion
* improved depth interaction
* surface-aware placement
* object interaction

### Creative Features

* advanced animations
* audio effects
* templates
* transitions
* advanced filters
* multi-subject scenes

### Cloud

* advanced inference
* cloud rendering
* optional project backup

### Social

Potential future features:

* sharing
* public creations
* templates
* community effects

These are explicitly outside the initial MVP.

---

# 14. Out of Scope for MVP

The MVP will not include:

* social networking
* follower systems
* comments
* messaging
* public feeds
* creator marketplace
* generative AI video
* generative AI image creation
* mandatory accounts
* complex cloud storage
* subscription/payment systems

---

# 15. Success Criteria

The MVP is considered successful when a user can:

```text
1. Capture/import an image
        ↓
2. Extract a person/object
        ↓
3. Save the extracted subject
        ↓
4. Place it into an AR scene
        ↓
5. Manipulate it naturally
        ↓
6. Place it into a creative canvas
        ↓
7. Record the result
        ↓
8. Save the result to the device
```

without requiring technical knowledge.

### Quality target

The application should feel like a **real consumer entertainment product**, not a university demonstration.

---

# 16. Key Product Principle

The product should hide technical complexity.

The user should think:

> **"I want to put this here."**

Not:

> "I need to select an AI model, configure segmentation, choose inference mode, configure AR anchors, and render the scene."

AI, AR, inference routing, rendering, and storage should work underneath the experience.

---

# 17. Primary Technical Risk

The highest-risk component is:

> **Reliable video subject extraction and temporal tracking while maintaining visually convincing compositing.**

The initial prototype should therefore validate:

```text
Video
  ↓
Segmentation
  ↓
Temporal consistency
  ↓
Subject tracking
  ↓
Transparent output
```

before significant effort is spent on advanced UI or social functionality.

---

# 18. Product North Star

### Core loop

```text
CAPTURE
   ↓
CUT
   ↓
PLACE
   ↓
PLAY
   ↓
RECORD
   ↓
SHARE
   ↓
CREATE AGAIN
```

### Product principle

> **AI underneath. Entertainment on top.**

### Long-term vision

Build a mobile creative platform where users can transform ordinary photos and videos into interactive AR and visual experiences using AI-powered subject extraction and intuitive spatial manipulation.

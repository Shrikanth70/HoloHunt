---
name: VYRA
colors:
  surface: '#0d1515'
  surface-dim: '#0d1515'
  surface-bright: '#333b3b'
  surface-container-lowest: '#080f10'
  surface-container-low: '#151d1e'
  surface-container: '#192122'
  surface-container-high: '#232b2c'
  surface-container-highest: '#2e3637'
  on-surface: '#dce4e5'
  on-surface-variant: '#b9cacb'
  inverse-surface: '#dce4e5'
  inverse-on-surface: '#2a3233'
  outline: '#849495'
  outline-variant: '#3b494b'
  surface-tint: '#00dbe9'
  primary: '#dbfcff'
  on-primary: '#00363a'
  primary-container: '#00f0ff'
  on-primary-container: '#006970'
  inverse-primary: '#006970'
  secondary: '#c8c6c8'
  on-secondary: '#303032'
  secondary-container: '#474649'
  on-secondary-container: '#b7b4b7'
  tertiary: '#f8f5f8'
  on-tertiary: '#303032'
  tertiary-container: '#dbd9db'
  on-tertiary-container: '#5f5e61'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#7df4ff'
  primary-fixed-dim: '#00dbe9'
  on-primary-fixed: '#002022'
  on-primary-fixed-variant: '#004f54'
  secondary-fixed: '#e4e2e4'
  secondary-fixed-dim: '#c8c6c8'
  on-secondary-fixed: '#1b1b1d'
  on-secondary-fixed-variant: '#474649'
  tertiary-fixed: '#e4e2e4'
  tertiary-fixed-dim: '#c8c6c8'
  on-tertiary-fixed: '#1b1b1d'
  on-tertiary-fixed-variant: '#474649'
  background: '#0d1515'
  on-background: '#dce4e5'
  surface-variant: '#2e3637'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 44px
    fontWeight: '700'
    lineHeight: 52px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.05em
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.1em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  unit: 8px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  2xl: 48px
  margin-mobile: 20px
  gutter-mobile: 12px
---

## Brand & Style
The design system for this product is built on a "Futuristic Minimalist" aesthetic, prioritizing immersion and creative focus. The brand personality is precise, high-tech, and premium, avoiding the common tropes of AI gradients in favor of a "Dark Chrome" look. 

The interface stays out of the way of the user's augmented reality content. It utilizes high-contrast accents against an OLED-optimized backdrop to create a sense of depth and technical sophistication. The emotional response should be one of empowerment and cinematic quality, moving away from "app-like" interfaces toward professional creative tools.

## Colors
The palette is centered around "Deep Space" neutrality and "Neon Cyan" energy. 

- **Primary:** Electric Cyan (#00F0FF) is the sole driver of action. It is used for active states, primary buttons, and critical UI indicators.
- **Background:** A near-black base (#0A0A0B) is used for the main UI shell, while true black (#000000) is reserved for full-screen AR viewports to ensure perfect OLED blending.
- **Secondary/Surface:** Charcoal (#161618) provides subtle separation for containers and secondary navigation elements without breaking the dark immersion.
- **Accents:** Use pure white for high-priority text and a muted grey (#8E8E93) for secondary metadata.

## Typography
The typography system uses **Inter** to maintain a clean, systematic, and highly legible appearance on Android devices. 

Large headlines are used to create a clear hierarchy in creative menus, while labels use slightly increased letter-spacing and uppercase styling for a "technical readout" feel. Scale is used aggressively; display sizes are prominent to establish the premium editorial tone, while body text remains utilitarian.

## Layout & Spacing
This design system follows an 8dp linear grid for all structural elements and a 4dp grid for fine-tuned internal component spacing (like icon-to-text ratios).

The layout philosophy is "Immersive Fixed." Elements are pinned to the edges of the screen to maximize the central AR viewport. 
- **Safe Areas:** Adhere to Android status and navigation bar heights, ensuring no interactive element is placed within 16px of the physical screen edge.
- **Margins:** A standard 20px side margin is used for text content and buttons to feel breathable but modern.
- **Grid:** Use a 4-column layout for mobile carousels and tool selection grids.

## Elevation & Depth
Depth is communicated through material properties rather than traditional drop shadows.

- **Level 0 (Background):** Deepest layer (#0A0A0B).
- **Level 1 (Containers):** Subtle charcoal (#161618) with no shadow, defined by a 1px inner stroke of (#FFFFFF, 8% opacity).
- **Overlays (Glassmorphism):** Used for persistent controls over AR content. Background blur (20px-30px) combined with a 20% opaque charcoal fill.
- **Active Elements:** Illuminated by the Primary color's glow. Use a subtle outer glow (0px 0px 12px) of the primary color only for the most critical active state (e.g., "Recording").

## Shapes
The shape language is "Hyper-Rounded." This creates a friendly, tactile contrast against the sharp, technical typography. 

Standard components (buttons, input fields) use a 16px radius. Large containers and full-screen overlays use a 24px radius at the top edges to mimic the physical curvature of modern Android handsets. Pill shapes are reserved exclusively for "Chips" and "Status Indicators."

## Components

### Buttons
- **Primary:** Filled with Electric Cyan (#00F0FF), black text. High contrast for immediate recognition.
- **Secondary:** Outlined with a 1.5px stroke of White (20% opacity). Text in White.
- **Ghost:** No background or border. Primary color text for actions, White text for navigation.

### Input Fields
- **Search/Text:** Background of Charcoal (#161618), 16px radius, minimal 1px border (#FFFFFF, 10% opacity). Placeholder text in muted grey.

### Chips & Selectors
- **Tool Chips:** Pill-shaped, semi-transparent background. When active, the border turns Electric Cyan and the background gains a subtle inner glow.

### Iconography
- **Style:** 24px bounding box, 1.5px stroke weight. 
- **Attributes:** Non-filled, geometric, and open-ended. Avoid "cute" or rounded-end caps; use square or miter joins for a technical look.

### Cards & Lists
- Minimize use of cards to avoid "social media" aesthetic. Instead, use thin horizontal dividers (1px, 8% White) and generous vertical spacing (24px) to separate content sections.
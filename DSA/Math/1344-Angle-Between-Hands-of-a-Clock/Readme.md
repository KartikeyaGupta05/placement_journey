# 1344. Angle Between Hands of a Clock

## Problem

Given two numbers `hour` and `minutes`, return the smaller angle (in degrees) formed between the hour and minute hand of a clock.

### Example 1

Input:

```text
hour = 12
minutes = 30
```

Output:

```text
165
```

### Example 2

Input:

```text
hour = 3
minutes = 30
```

Output:

```text
75
```

### Example 3

Input:

```text
hour = 3
minutes = 15
```

Output:

```text
7.5
```

---

## Approach

The hour hand moves:

```text
360° / 12 = 30° per hour
```

The minute hand moves:

```text
360° / 60 = 6° per minute
```

The hour hand also moves as minutes pass.

```text
30° / 60 = 0.5° per minute
```

Therefore:

```text
Hour Hand Angle   = 30 × hour + 0.5 × minutes
Minute Hand Angle = 6 × minutes
```

Angle between them:

```text
| (30 × hour + 0.5 × minutes) - (6 × minutes) |
```

```text
| 30 × hour - 11/2 × minutes |
```

Since a clock forms two angles, return:

```text
min(angle, 360 - angle)
```

---

## Dry Run

Input:

```text
hour = 3
minutes = 15
```

Using the formula:

```text
angle = |30 × 3 - (11/2 × 15)|
```

```text
= |90 - 82.5|
```

```text
= 7.5
```

Smaller angle:

```text
min(7.5, 360 - 7.5)
```

```text
= 7.5
```

Output:

```text
7.5
```

---

## Time Complexity

```text
O(1)
```

Only a few arithmetic operations are performed.

---

## Space Complexity

```text
O(1)
```

No extra space is used.

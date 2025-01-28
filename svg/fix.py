import numpy as np
from svgpathtools import svg2paths, wsvg
from svgpathtools.path import Path, Line, CubicBezier, QuadraticBezier, Arc
from typing import Tuple

def interpolate_points(p1: complex, p2: complex, factor: float = 0.5) -> complex:
    """Interpolate between two points with a given factor."""
    return p1 + (p2 - p1) * factor

def smooth_segment(segment, smoothing_factor: float = 0.5):
    """Smooth a path segment (line, bezier, arc)."""
    if isinstance(segment, Line):
        start, end = segment.start, segment.end
        control1 = interpolate_points(start, end, smoothing_factor / 3)
        control2 = interpolate_points(start, end, 2 * smoothing_factor / 3)
        return CubicBezier(start, control1, control2, end)
    elif isinstance(segment, QuadraticBezier):
        control1 = interpolate_points(segment.start, segment.control, smoothing_factor)
        control2 = interpolate_points(segment.control, segment.end, smoothing_factor)
        return CubicBezier(segment.start, control1, control2, segment.end)
    elif isinstance(segment, Arc):
        # Convert Arc to CubicBezier for smoothing (approximation)
        start, end = segment.start, segment.end
        control1 = interpolate_points(start, end, smoothing_factor / 3)
        control2 = interpolate_points(start, end, 2 * smoothing_factor / 3)
        return CubicBezier(start, control1, control2, end)
    return segment

def merge_gaps(path: Path, threshold: float = 0.1) -> Path:
    """Merge small gaps between path segments."""
    new_path = Path()
    last_point = None
    for segment in path:
        if last_point is not None and np.linalg.norm([segment.start.real - last_point.real,
                                                      segment.start.imag - last_point.imag]) > threshold:
            new_path.append(Line(last_point, segment.start))
        new_path.append(segment)
        last_point = segment.end
    return new_path

def smooth_svg(input_file: str, output_file: str, smoothing_factor: float = 0.5, gap_threshold: float = 0.1):
    """Smooth and merge gaps in an SVG file."""
    paths, attributes = svg2paths(input_file)
    smoothed_paths = []

    for path in paths:
        merged_path = merge_gaps(path, threshold=gap_threshold)
        new_path = Path()

        for segment in merged_path:
            new_segment = smooth_segment(segment, smoothing_factor)
            new_path.append(new_segment)

        smoothed_paths.append(new_path)  # Store smoothed path

    wsvg(smoothed_paths, attributes=attributes, filename=output_file)

if __name__ == "__main__":
    input_svg = "Cloudy.svg"  # Replace with your input SVG file path
    output_svg = "output.svg"  # Replace with your desired output SVG file path
    smoothing_factor = 0.7  # Adjust this value for more or less smoothing
    gap_threshold = 0.5  # Adjust this value based on your SVG's scale and precision
    smooth_svg(input_svg, output_svg, smoothing_factor, gap_threshold)
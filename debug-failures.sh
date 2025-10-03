#!/bin/bash

# Debug Traces Script
# Helps you quickly view test traces

echo "🔍 Finding test traces..."
echo ""

# Find the latest test run folder
LATEST_RUN=$(ls -t test-results/traces/ 2>/dev/null | head -1)

if [ -z "$LATEST_RUN" ]; then
    echo "❌ No traces found. Run 'mvn test' first."
    exit 1
fi

TRACE_DIR="test-results/traces/$LATEST_RUN"
echo "📂 Latest test run: $TRACE_DIR"
echo ""

# List all trace files
TRACES=$(find "$TRACE_DIR" -name "*.zip" 2>/dev/null | sort)

if [ -z "$TRACES" ]; then
    echo "❌ No traces found."
    exit 0
fi

# Count traces
TRACE_COUNT=$(echo "$TRACES" | wc -l | tr -d ' ')
echo "📦 Found $TRACE_COUNT test trace(s):"
echo ""

# Display traces with numbers
i=1
while IFS= read -r trace; do
    filename=$(basename "$trace")
    echo "  [$i] $filename"
    i=$((i+1))
done <<< "$TRACES"

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

# Ask which trace to view
if [ $TRACE_COUNT -eq 1 ]; then
    echo "Opening trace viewer..."
    npx playwright show-trace "$TRACES"
else
    read -p "Enter trace number to view (1-$TRACE_COUNT) or 'a' for all: " choice
    echo ""

    if [ "$choice" = "a" ] || [ "$choice" = "A" ]; then
        echo "Opening all traces..."
        while IFS= read -r trace; do
            npx playwright show-trace "$trace" &
        done <<< "$TRACES"
    elif [ "$choice" -ge 1 ] && [ "$choice" -le $TRACE_COUNT ] 2>/dev/null; then
        SELECTED_TRACE=$(echo "$TRACES" | sed -n "${choice}p")
        echo "Opening trace: $(basename "$SELECTED_TRACE")"
        npx playwright show-trace "$SELECTED_TRACE"
    else
        echo "❌ Invalid choice"
        exit 1
    fi
fi

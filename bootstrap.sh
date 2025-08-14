#!/data/data/com.termux/files/usr/bin/bash
cd "$HOME"

echo "⏱ Starting JarvisX quick bootstrap..."

# Step 1: Ensure necessary packages are installed
pkg update -y && pkg upgrade -y
pkg install -y git python nodejs
echo "✅ Required packages installed."

# Step 2: Pull latest code or clone if missing
if [ -d "JarvisX" ]; then
  cd JarvisX
  echo "📡 Pulling latest from GitHub..."
  git pull
else
  echo "⏳ Cloning your JarvisX repository..."
  git clone https://github.com/TeeDone2/JarvisX.git
  cd JarvisX
fi

# Step 3: Install Node + Python deps if they exist
if [ -f package.json ]; then
  echo "📦 Installing Node dependencies..."
  npm install
fi
if [ -f requirements.txt ]; then
  echo "📦 Installing Python dependencies..."
  pip install -r requirements.txt
fi

# Step 4: Kick off build (if build script exists)
if [ -f ".github/workflows/android.yml" ]; then
  echo "🤖 Triggering GitHub Actions build..."
  # This assumes the GitHub Actions workflow is already set up
  gh run workflow run android.yml --repo TeeDone2/JarvisX
else
  echo "⚠ No Actions workflow detected—skipping."
fi

echo "🎉 Bootstrap complete!"

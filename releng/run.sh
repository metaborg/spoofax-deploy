#!/usr/bin/env bash

set -eu

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Determine Python executable
if command -v python3 >/dev/null 2>&1; then
  PYTHON_CMD='python3'
elif command -v python >/dev/null 2>&1; then
  PYTHON_CMD='python'
else
  echo "Cannot find 'python3' or 'python' interpreter, please install Python 3"
  exit 1
fi

VENV="$DIR/.venv"

# Create virtual environment, if it does not exist yet
if [ ! -f "$VENV/bin/activate" ]
then
    if ! command -v virtualenv &> /dev/null
    then
        echo "Could not find the Python 'virtualenv' command, which is required for creating a"
        echo "Python virtual environment. There are a few options to fix this, for example:"
        echo "- Install virtualenv using Pip, e.g.:"
        echo "    $PYTHON_CMD -m pip install virtualenv"
        echo "- Create the virtual environment manually without Pip and install Pip inside it:"
        # This fix comes from comment #58 on the Ubuntu bug report
        echo "    $PYTHON_CMD -m venv \"$VENV\""
        echo "After executing one of these options, you can re-run this command."
        exit 1
    fi

    # This command may print to stdout
    virtualenv "$VENV"
fi

# Activate virtual environment
# (Temporarily disable unbound variable checks when activating virtual environment (was necessary for venv, still necessary for virtualenv?))
set +o nounset
source "$VENV/bin/activate"

# Upgrade pip
$PYTHON_CMD -m pip install --upgrade pip --quiet

# Install requirements
$PYTHON_CMD -m pip install --quiet --requirement "$DIR/requirements.txt"

# Run script
$PYTHON_CMD -u "$DIR/main.py" $*

# Deactivate virtualenv
deactivate
set -o nounset

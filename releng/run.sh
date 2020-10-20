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

# Create virtual environment
if [[ ! -d "$VENV" ]]
then
    if ! $PYTHON_CMD -m venv "$VENV" > /dev/null
    then
        # On Ubuntu/Debian, Python3 does not automatically come with ensurepip installed, so we install it manually:
        # https://bugs.launchpad.net/ubuntu/+source/python3.4/+bug/1290847/+index?comments=all (comment #58)
        $PYTHON_CMD -m venv --without-pip "$VENV" > /dev/null
        curl -s https://bootstrap.pypa.io/get-pip.py | "$VENV/bin/python" > /dev/null
    fi
fi

# Activate virtual environment
source "$VENV/bin/activate"

# Install requirements
$PYTHON_CMD -m pip install --quiet --requirement "$DIR/requirements.txt"

# Run script
$PYTHON_CMD -u "$DIR/main.py" $*

# Deactivate virtualenv
deactivate

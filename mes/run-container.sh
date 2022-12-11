
#!/bin/bash

# allow access from localhost
xhost + 127.0.0.1

# Run the image builded.
docker run -e DISPLAY=host.docker.internal:0 --privileged mes

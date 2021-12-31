# cef-fx-demo

This repository contains demo code to see how one can use https://github.com/BestSolution-at/cef-fx

## Running the code

### Windows & Linux

Running the code on those platforms should be straight forward just clone the repo and launch the application.

### MacOSX

Running on OS-X is a bit harder because currently the binaries are not signed. OS-X might refuse to launch them. 
Work to sign the artifacts is on the TODO-List. As we currently only ship x64 binaries you need to have Rosetta-2 
installed if you are running on the apple silicon.

## Troubleshooting

### CEF-FX does not boot into the Event-Loop

On the first run CEF extracts its binaries to a temp folder. As CEF-FX is immature yet and there are raise conditions
it might be that.

If you see this output for more than 30 seconds CEF was not able to run the event loop and you need to forcefully kill the process
```
Extracting /var/folders/hk/j16y787547735rl7f6tpnwp00000gn/T/ceffx-native_088202f448e1e3d1509491bcfb0c271ca53cbb1e1c9dcdb6360e1ea8909d433d/CEF-fx.app/Contents/Frameworks/CEF-fx Helper (Renderer).app/Contents/MacOS/CEF-fx Helper (Renderer)
Native CEF files located at /var/folders/hk/j16y787547735rl7f6tpnwp00000gn/T/ceffx-native_088202f448e1e3d1509491bcfb0c271ca53cbb1e1c9dcdb6360e1ea8909d433d
```

In contrast to that a successful boot process looks like this:

```
Native CEF files located at /var/folders/hk/j16y787547735rl7f6tpnwp00000gn/T/ceffx-native_088202f448e1e3d1509491bcfb0c271ca53cbb1e1c9dcdb6360e1ea8909d433d
[1231/180508.700886:VERBOSE1:pref_proxy_config_tracker_impl.cc(186)] 0x7fb617707e60: set chrome proxy config service to 0x7fb62770dab0
[1231/180508.767095:VERBOSE1:webrtc_internals.cc(118)] Could not get the download directory.
[1231/180508.781721:VERBOSE1:media_stream_manager.cc(705)] MSM::InitializeMaybeAsync([this=0x7fb607704930])
[1231/180508.782773:VERBOSE1:media_stream_manager.cc(705)] MDM::MediaDevicesManager()
[1231/180508.782823:VERBOSE1:media_stream_manager.cc(705)] MSM::MediaStreamManager([this=0x7fb607704930]))
[1231/180508.787855:INFO:CefUtil.cpp(66)] CEF initialized
[1231/180508.787902:INFO:CefUtil.cpp(71)] running message loop...
```

### Cleanup after forcefully killing

In case you force fully killed the application it might be that the external process is still running search for the `cef-fx.native-standalone` binary and kill it

from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice, MonkeyImage

shortSleepInterval = 0.5

# sets a variable with the package's internal name
package = 'uk.co.vurt.hakken'

# sets a variable with the name of an Activity in the package
activity = 'uk.co.vurt.hakken.activities.DispatcherActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# expected image directory
expectedDir = 'src/test/resources/expected_screenshots'

device = None

#Utility method for taking a screenshot
#Loops, comparing subsequent images until result is stable
#working around issues with framebuffers and timing
def screenShot(filename) :
    stableImage = False

    while not stableImage:
        result = device.takeSnapshot()
        MonkeyRunner.sleep(shortSleepInterval)
        result2 = device.takeSnapshot()
        stableImage = result2.sameAs(result, 0.5)
        # print stableImage, ' -- image stabilizing...'

    MonkeyRunner.sleep(shortSleepInterval)
    result2.writeToFile(filename,'png') 
    return result2

def compare(expectedFilename, actualImage) :
    expectedImage = MonkeyRunner.loadImageFromFile(expectedDir + '/' + expectedFilename)
    return expectedImage.sameAs(actualImage, 0.5)
         
# Connects to the current device, returning a MonkeyDevice object
def waitForConnection():
    global device
    device = MonkeyRunner.waitForConnection()
        
def testEmptyStartup() :
    # Runs the component
    device.startActivity(component=runComponent)
    actual = screenShot('test_results/test1_preferences_screen.png')
    return compare('test1_preferences_screen.png', actual)
    
def testOpeningSyncServerDialog() :
    waitForConnection()
    device.touch(237, 178, 'DOWN_AND_UP')
    actual = screenShot('test_results/test2_syncserver_dialog.png')
    return compare('test2_syncserver_dialog.png', actual)
    
def testTypingSyncServer() :
    waitForConnection()
    device.type('http://wm-vms-stf-01.wmfs.net:8280/hakken')
    actual = screenShot('test_results/test3_synserver_entered.png')
    return compare('test3_synserver_entered.png', actual)
    
def testCloseSyncServerDialog() :
    waitForConnection()
    device.touch(136, 509, 'DOWN_AND_UP')
    actual = screenShot('test_results/test4_synserver_close.png')
    return compare('test4_synserver_close.png', actual)
    
def testCloseSettings():
    waitForConnection()
    device.touch(236, 751, 'DOWN_AND_UP')
    actual = screenShot('test_results/test5_settings_closed.png')
    return compare('test5_settings_closed.png', actual)
    
def setup() :
    waitForConnection()
    
    #Remove any existing package and data, to ensure we're at a default state.
    device.removePackage(package)
    
    # Installs the Android package. Notice that this method returns a boolean, so you can test
    # to see if the installation worked.
    device.installPackage('target/hakken.apk')
    
if __name__ == '__main__':
    setup()
    passed = 0
    
    tests = [testEmptyStartup,
        testOpeningSyncServerDialog,
        testTypingSyncServer,
        testCloseSyncServerDialog,
        testCloseSettings]
        
    for test in tests:
        result = test()
        print "%s: %s" % (test.__name__, result)
        if result:
            passed += 1
                    
    print "Status: %d/%d" % (passed, len(tests)) 

/**
 * This package contains the interfaces for external applications to interact with the game engine. 
 * These interfaces are designed to be used by external applications, such as an AI client, 
 * to send commands to the game engine and receive responses.
 * 
 * APIs in this package are designed to use Strings as parameters to avoid coupling external applications
 * to the internal data structures of the game engine.  There is a layer that turns these String-based 
 * commands into the internal data structures used by the game engine.
 */
package com.rogers.rmcdouga.fitg.basegame.command.api.external;
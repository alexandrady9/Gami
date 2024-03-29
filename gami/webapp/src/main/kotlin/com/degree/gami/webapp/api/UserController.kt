package com.degree.gami.webapp.api

import com.degree.gami.model.*
import com.degree.gami.service.principal.PrincipalService
import com.degree.gami.service.user.UserConverter
import com.degree.gami.service.user.UserService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/user"])
class UserController(private val userService: UserService,
                     private val principalService: PrincipalService,
                     private val userConverter: UserConverter) {

    @GetMapping(value = ["{username}/photos"])
    fun getPhotos(@Valid @PathVariable username: String): List<PhotoDao> {
        return userService.getPhotos(username)
    }

    @PutMapping(value = ["{username}/photos"])
    fun setPhotos(@Valid @PathVariable username: String,@Valid @RequestBody photo: PhotoDao) {
        userService.setPhotos(username, photo.image)
    }

    @GetMapping(value = ["/bookmarks"])
    fun getEventsByBookmark() : List<EventDao>{
        return userService.getEventsByBookmark()
    }

    @GetMapping(value = ["/events"])
    fun getEventByUser(): List<EventDao> {
        return userService.getEvents()
    }

    @PutMapping
    fun update(@Valid @RequestBody userDao: UserDao) : UserDao {
        return userService.update(userDao)
    }

    @PostMapping(value = ["bookmark/{eventName}"])
    fun bookmarkEvent(@Valid @PathVariable eventName: String) {
        userService.bookmarkEvent(eventName)
    }

    @DeleteMapping(value = ["bookmark/{eventName}"])
    fun removeBookmarkEvent(@Valid @PathVariable eventName: String) {
        userService.removeBookmarkEvent(eventName)
    }

    @PostMapping(value = ["join/{eventName}"])
    fun joinEvent(@Valid @PathVariable eventName: String) : Boolean {
        return userService.joinEvent(eventName)
    }

    @DeleteMapping(value = ["join/{eventName}"])
    fun leftEvent(@Valid @PathVariable eventName: String) {
        userService.leftEvent(eventName)
    }

    @GetMapping(value = ["/principal"])
    fun getPrincipal(@RequestHeader("Authorization") auth: String, response: HttpServletResponse): UserDao {
        val user = principalService.getPrincipal()
        response.addHeader("Authorization", auth)
        return userConverter.convertToDao(user)!!
    }

    @PostMapping(value = ["/register"])
    fun register(@Valid @RequestBody user: UserDao) : UserDao {
        userService.register(user)
        return user
    }

    @PostMapping(value = ["/sendMail"])
    fun sendMail(@Valid @RequestBody sendMail: SendMailDao) : Boolean {
        return userService.sendMail(sendMail)
    }

    @PutMapping(value = ["/changePassword"])
    fun changePassword(@Valid @RequestBody changePasswordDao: ChangePasswordDao) {
        userService.changePassword(changePasswordDao)
    }

    @GetMapping(value = ["/byUsername/{username}"])
    fun getUserByUsername(@Valid @PathVariable username: String): UserDao {
        return userService.getUserByUsername(username)
    }

    @GetMapping(value = ["{username}/hostedEvents"])
    fun getHostedEvents(@Valid @PathVariable username: String): List<EventDao> {
        return userService.getHostedEvents(username)
    }
}
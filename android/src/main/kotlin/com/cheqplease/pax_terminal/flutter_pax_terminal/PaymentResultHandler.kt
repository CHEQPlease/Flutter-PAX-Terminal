package com.cheqplease.pax_terminal.flutter_pax_terminal

interface PaymentSuccessHandler<T> {

    fun onSuccess(response : T)

}

interface PaymentFailureHandler<T>{
    fun onFailure(response: T)
}

package dogs


fun main() {
    DogsRepository.getInstance("sango").dogs.forEach ( ::println )
}
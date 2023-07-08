#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define NUM_GROUPS 6
#define PRESENTATION_DURATION 5
#define WAIT_TIME 2

void runPresentation(char group) {
    printf("Groupe %c présente son sujet\n", group);
    fflush(stdout);
    sleep(PRESENTATION_DURATION);  // Attente de la durée de présentation en secondes
    printf("Le groupe %c a terminé sa présentation\n", group);
    fflush(stdout);
}

int main() {
    printf("Début de l'exposé Round Robin\n");

    char groups[NUM_GROUPS] = {'A', 'B', 'C', 'D', 'E', 'F'};
    int current_group = 0;

    while (current_group < NUM_GROUPS) {
        runPresentation(groups[current_group]);
        current_group++;

        if (current_group < NUM_GROUPS) {
            printf("Attente de %d secondes avant que le groupe %c commence sa présentation\n", WAIT_TIME, groups[current_group]);
            fflush(stdout);
            sleep(WAIT_TIME);
        }

        
    }

    printf("Fin de l'exposé Round Robin\n");

    return 0;
}

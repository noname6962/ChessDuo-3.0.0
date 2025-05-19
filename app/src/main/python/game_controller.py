import game_view
import game_model
import pygame

# pygame setup
pygame.init()
screen = pygame.display.set_mode((480, 480))
pygame.display.set_caption("chess")
clock = pygame.time.Clock()
running = True


Biale_figury = [('W', 1, 0, 0), ('S', 1, 1, 0), ('G', 1, 2, 0), ('K', 1, 3, 0), ('D', 1, 4, 0), ('G', 1, 5, 0), ('S', 1, 6, 0), ('W', 1, 7, 0),
                ('P', 1, 0, 1), ('P', 1, 1, 1), ('P', 1, 2, 1), ('P', 1, 3, 1), ('P', 1, 4, 1), ('P', 1, 5, 1), ('P', 1, 6, 1), ('P', 1, 7, 1)]

Czarne_figury = [('W', 1, 0, 7), ('S', 1, 1, 7), ('G', 1, 2, 7), ('K', 1, 3, 7), ('D', 1, 4, 7), ('G', 1, 5, 7), ('S', 1, 6, 7), ('W', 1, 7, 7),
                 ('P', 1, 0, 6), ('P', 1, 1, 6), ('P', 1, 2, 6), ('P', 1, 3, 6), ('P', 1, 4, 6), ('P', 1, 5, 6), ('P', 1, 6, 6), ('P', 1, 7, 6)]

game_model.pozycje(Biale_figury, Czarne_figury)

game_view.odswiezanie(screen, Biale_figury, Czarne_figury)

tura = 0
moves = []

pygame.display.flip()

while running:
    # pygame.QUIT event means the user clicked X to close your window
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        elif event.type == pygame.MOUSEBUTTONDOWN:
            press_x, press_y = pygame.mouse.get_pos()
        elif event.type == pygame.MOUSEBUTTONUP:
            release_x, release_y = pygame.mouse.get_pos()
            press_x, press_y, release_x, release_y, vec_x, vec_y = game_model.przesuniecie(press_x, press_y, release_x, release_y)
            if game_model.ruch(Biale_figury, Czarne_figury, press_x, press_y, vec_x, vec_y, tura, moves):
                tura += 1
                print(moves)
                game_view.odswiezanie(screen, Biale_figury, Czarne_figury)
                pygame.display.flip()
                if game_model.mat(Biale_figury, Czarne_figury, tura, moves):
                    running = False

    clock.tick(60)  # limits FPS to 30

pygame.quit()

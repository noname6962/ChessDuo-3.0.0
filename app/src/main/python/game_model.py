import move
import szach

def validate_wrapper(white, black, x, y, vec_x, vec_y, turn, moves):
    import collections.abc

    def to_py_list(java_list):
        return [list(item) if isinstance(item, collections.abc.Iterable) and not isinstance(item, (str, bytes)) else item
                for item in java_list]

    white = to_py_list(white)
    black = to_py_list(black)
    moves = to_py_list(moves)

    return ruch(white, black, x, y, vec_x, vec_y, turn, moves)





def ruch(biale_figury, czarne_figury, x, y, vec_x, vec_y, tura, moves):
    biale_pozycje, czarne_pozycje = pozycje(biale_figury, czarne_figury)
    if move.move(biale_figury, czarne_figury, biale_pozycje, czarne_pozycje, x, y, vec_x, vec_y, tura, moves):
        return 1
    return 0  # Explicitly return 0 for invalid move



def mat(biale_figury, czarne_figury, tura, moves):
    biale_pozycje, czarne_pozycje = pozycje(biale_figury, czarne_figury)
    if szach.check_mat(biale_figury, czarne_figury, biale_pozycje, czarne_pozycje, tura, moves):
        return 1


def pozycje(biale_figury, czarne_figury):

    biale_pozycje = []
    czarne_pozycje = []

    for figury in biale_figury:
        if figury[1] == 1:
            biale_pozycje.append((figury[2], figury[3]))
    for figury in czarne_figury:
        if figury[1] == 1:
            czarne_pozycje.append((figury[2], figury[3]))

    return biale_pozycje, czarne_pozycje


def przesuniecie(press_x, press_y, release_x, release_y):
    press_x = press_x // 60
    press_y = press_y // 60
    release_x = release_x // 60
    release_y = release_y // 60

    vec_x = release_x - press_x
    vec_y = release_y - press_y
    return press_x, press_y, release_x, release_y, vec_x, vec_y

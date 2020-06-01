# GalleriesProject
# JPA PART  
Настроить persistence.xml под свою бд. Есть класс DatabaseUtility, который заполняет базу (для того, чтобы можно было в main посмотреть, что выводит логгер, например)
Написаны тесты
# MARKUP PART 
файлы json хранятся в папке resouces
# FE-PART
                   А        . Были изменены связи между сущностями, чтобы избежать circle references.
• Gallery(int id, String name, String city) - галлерея, в которой могут выставляться несколько художников.

• Painter(int id, String name, Studio studio, List<Painting> paintings, Gallery gallery) - художник, в котором находится информация о картинах, галлереях, студии и проч
    
• Painting(int id, String name)

• Studio(int id, String name) - студия, в которой работает художник
    
                                                Б. Реализованы следующие отношения
                                 (NB!) Еще в разработке. Скорее всего, поменяю некоторые связи.
    
•Painter-Studio -> OneToOne

•Painter-Gallery -> ManyToOne

•Painter-Paintings -> OneToMany

                                                 C. Были добавлены контроллеры и тесты

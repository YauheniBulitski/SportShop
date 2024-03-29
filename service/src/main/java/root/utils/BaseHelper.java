package root.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import root.entity.*;
import root.service.*;

import java.math.BigDecimal;

@Component
public class BaseHelper {

    private final CategoryService categoryService;
    private final CountryService countryService;
    private final MakerService makerService;
    private final ProductService productService;
    private final RoleService roleService;
    private final TypeService typeService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CountService countService;

    @Autowired
    public BaseHelper(CategoryService categoryService,
                      CountryService countryService,
                      MakerService makerService,
                      ProductService productService,
                      RoleService roleService,
                      TypeService typeService,
                      UserService userService,
                      PasswordEncoder passwordEncoder,
                      CountService countService) {
        this.categoryService = categoryService;
        this.countryService = countryService;
        this.makerService = makerService;
        this.productService = productService;
        this.roleService = roleService;
        this.typeService = typeService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.countService = countService;
    }


    public void data() {

        Role adminRole = roleService.save(Role.builder().name("ADMIN").build());
        Role userRole = roleService.save(Role.builder().name("USER").build());

        User user = userService.save(User.builder()
                .userName("Vasya")
                .name("User")
                .password(passwordEncoder.encode("2222"))
                .phoneNumber("+375291234567")
                .role(userRole)
                .build());
        User admin = userService.save(User.builder()
                .userName("Petya")
                .name("Admin")
                .password(passwordEncoder.encode("1111"))
                .phoneNumber("+375297654321")
                .role(adminRole)
                .build());

        Category sports_nutrition = categoryService.save(Category.builder()
                .name("Спортивное питание").build());
        Category sports_wear = categoryService.save(Category.builder()
                .name("Спортивная одежда").build());
        Category weightlifting = categoryService.save(Category.builder()
                .name("Тяжелая атлетика").build());
        Category simulators = categoryService.save(Category.builder()
                .name("Тренажеры").build());

        Type glover=typeService.save(Type.builder().name("Перчатки")
        .category(sports_wear).build());
        Type shorts=typeService.save(Type.builder().name("Шорты")
        .category(sports_wear).build());
        Type tShirts=typeService.save(Type.builder().name("Майки")
        .category(sports_wear).build());
        Type tops=typeService.save(Type.builder().name("Топы")
                .category(sports_wear).build());
        Type leggings=typeService.save(Type.builder().name("Леггинсы")
                .category(sports_wear).build());

        Type protein = typeService.save(Type.builder().name("Протеин")
                .category(sports_nutrition).build());
        Type gainer = typeService.save(Type.builder().name("Гейнер")
                .category(sports_nutrition).build());
        Type creatine = typeService.save(Type.builder().name("Креатин")
                .category(sports_nutrition).build());
        Type vitamins = typeService.save(Type.builder().name("Витамины")
                .category(sports_nutrition).build());

        Type exercise_bike = typeService.save(Type.builder().name("Велотренажеры")
                .category(simulators).build());
        Type treadmills = typeService.save(Type.builder().name("Беговые дорожки")
                .category(simulators).build());
        Type strength_training = typeService.save(Type.builder().name("Силовые тренажеры")
                .category(simulators).build());
        Type horizontal_bars = typeService.save(Type.builder().name("Турники")
                .category(simulators).build());

        Type disc=typeService.save(Type.builder().name("Диски").category(weightlifting).build());
        Type dumbbell=typeService.save(Type.builder().name("Гантели").category(weightlifting).build());
        Type barbell=typeService.save(Type.builder().name("Штанги").category(weightlifting).build());
        Country poland = countryService.save(Country.builder().name("Poland").abbreviation("PL").build());
        Country germany = countryService.save(Country.builder().name("Germany").abbreviation("DE").build());
        Country china = countryService.save(Country.builder().name("China").abbreviation("CH").build());
        Maker trec = makerService.save(Maker.builder().name("Trec Nutrition").country(poland).build());
        Maker strimex = makerService.save(Maker.builder().name("Strimex").country(germany).build());
        Maker alpin = makerService.save(Maker.builder().name("Alpin").country(china).build());


        for (int i=0;i<10;i++){
            countService.save(45,productService.save(Product.builder()
                    .name("Перчатки Man’s Power PS-258" +i)
                    .price(BigDecimal.valueOf(10.25 + i)).maker(alpin).type(glover)
                    .description("Перчатки для занятий в тренажерном зале вместе с VELVETEX" +
                            " обеспечивают полную свободу движения и циркуляцию воздуха.")
                    .url("/resources/images/glover.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Шорты компрессионные черно-розовые M18" +i)
                    .price(BigDecimal.valueOf(20.50 + i)).maker(alpin).type(shorts)
                    .description("Шорты для женщин, которые хотят чувствовать себя комфортно и быть сексуальной во время тренировки")
                    .url("/resources/images/shorts.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("TECH LONG SLEEVE TOP 12" +i)
                    .price(BigDecimal.valueOf(30.50 + i)).maker(alpin).type(tShirts)
                    .description("Данный элемент гардероба отлично подойдет для спортивных занятий и повседневной жизни, украсит образ" +
                            " активного мужчины, который любит находится в движении и при этом чувствовать себя легко и уверенно.")
                    .url("/resources/images/tShirts.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Топ MINI TOP SUPPLEX 20" +i)
                    .price(BigDecimal.valueOf(34.60 + i)).maker(strimex).type(tops)
                    .description("Топ подойдет для фитнеса, бега, танцев, посещения тренажерного зала, велоспорта, командных игр и других спортивных направлений.")
                    .url("/resources/images/tops.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Лосины LEGGINGS CAMO AW 115" +i)
                    .price(BigDecimal.valueOf(44.60 + i)).maker(strimex).type(leggings)
                    .description("Над созданием данной модели работал не только главный дизайнер производителя NEBBIA, но и целая команда бодибилдеров, " +
                            "поэтому изделие соответствует всем запросам современных атлетов.")
                    .url("/resources/images/leggings.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Комплекс ViMiLine (" +i*10+" табл)")
                    .price(BigDecimal.valueOf(34.20 + i)).maker(strimex).type(vitamins)
                    .description("Созданный специально для мужчин комплекс ключевых минералов и экзотических фито-добавок VitaMen способствует улучшению умственной" +
                            " и физической активности, здоровью простаты, снижает негативные последствия стресса и нормализует функцию пищеварительной системы.")
                    .url("/resources/images/vitamins.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Диск обрезиненный KP" +i*10+" кг")
                    .price(BigDecimal.valueOf(24.20 + i*2)).maker(strimex).type(disc)
                    .description("Черный, посадочное отверстие d=51 mm. Состав товара: Сталь, резина")
                    .url("/resources/images/discs.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Штанга OB86" +i)
                    .price(BigDecimal.valueOf(34.20 + i*2)).maker(alpin).type(barbell)
                    .description("Гриф (штанга). Хват d=28 mm, посадка d=50mm, замки.")
                    .url("/resources/images/barbell.jpg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Гантели разборные Lite Weights 2*1" +i+" кг")
                    .price(BigDecimal.valueOf(38.00 + i*2)).maker(alpin).type(dumbbell)
                    .description("комплектность: диски (12шт.) наполнитель: цементная смесь\n" +
                            "материалы: стержень - чугун" +
                            "покрытие: полипропилен (цвет - черный)" +
                            "замки: (4шт.)")
                    .url("/resources/images/dumb.jpeg")
                    .build()));

            countService.save(45,productService.save(Product.builder()
                    .name("Турник Absolute Champion 3 треххватный RT"+i)
                    .price(BigDecimal.valueOf(48.00 + i*2)).maker(alpin).type(horizontal_bars)
                    .description("Турник для подтягивания различным хватом: прямым широким хватом " +
                            "и прямым обычным, узким хватом, а также торцевым или обратным хватом.")
                    .url("/resources/images/bars.jpg")
                    .build()));
        }








        Product p1 = productService.save(Product.builder()
                .name("TREC NUTRITION BOOSTER WHEY PROTEIN(700ГР)")
                .price(BigDecimal.valueOf(20.25)).maker(trec).type(protein)
                .description("Trec Nutrition Booster Whey Protein – полноценная и невероятно вкусная белковая подпитка ваших мышц!" +
                        " Ценители поистине вкусных, насыщенных и питательных протеиновых коктейлей с густой текстурой будут в восторге")
//                        " когда попробуют великолепный белковый шейк Booster Whey Protein, представленный компанией Trec Nutrition.")
                .url("/resources/images/prot.jpg")
                .build());
        countService.save(54, p1);

        Product p2 =  productService.save(Product.builder()
                .name("TREC NUTRITION CASEIN 100 (600 ГР)").
                        price(BigDecimal.valueOf(29.85)).maker(trec).type(protein)
                .description("В состав CASEIN 100 входит 100% мицелярного казеина, который является прекрасным источником полноценного полезного" +
                        " протеина с продолжительным сроком усваивания. Продукт обеспечивает многочасовое снабжение мышц всеми необходимыми аминокислотами")
//                        " в том числе в большом количестве ВСАА и L-глютамин. CASEIN 100 защищает от ночного и посттренировочного катаболизма, стимулируя регенерацию " +
//                        "и развитие мышечной ткани.")
                .url("/resources/images/prot.jpg")
                .build());
        countService.save(25, p2);

        Product p3 = productService.save(Product.builder()
                .name("TREC NUTRITION WHEY 100 (900 ГР)").price(BigDecimal.valueOf(20.75)).maker(trec).type(protein)
                .description("Сывороточный протеин  Whey 100 от Trec Nutrition – это великолепный протеин для роста мышц и формирования рельефа тела на " +
                        "основе легкоусвояемого концентрата сывороточного белка. Whey 100 способен стимулировать процесс анаболизма")
//                        " нагрузках приводит к гипертрофии мышечной массы, к которой стремится каждый атлет. Также продукт подойдет людям, которые стремятся повысить" +
//                        " содержание белка в ежедневном рационе вне зависимости от физических нагрузок.")
                .url("/resources/images/prot.jpg")
                .build());
        countService.save(45, p3);

        Product p4 =productService.save(Product.builder()
                .name("TREC NUTRITION SOY PROTEIN ISOLATE (1250 ГР)")
                .price(BigDecimal.valueOf(30.75)).maker(trec).type(protein)
                .description("Soy Protein Isolate помогает поддерживать мышечную ткань, способствуя развитию спортивной фигуры. Продукт рекомендуется в качестве белковой" +
                        " добавки для применения в ежедневном рационе питания физически активных людей.")
                .url("/resources/images/prot.jpg")
                .build());
        countService.save(85, p4);

        Product p5 =productService.save(Product.builder()
                .name("TREC NUTRITION SOY PROTEIN ISOLATE (650 ГР)")
                .price(BigDecimal.valueOf(20.75)).maker(trec).type(protein)
                .description("Soy Protein Isolate помогает поддерживать мышечную ткань, способствуя развитию спортивной фигуры. Продукт рекомендуется в качестве белковой" +
                        " добавки для применения в ежедневном рационе питания физически активных людей.")
                .url("/resources/images/prot.jpg")
                .build());
        countService.save(25, p5);

        Product p6 =productService.save(Product.builder()
                .name("TREC NUTRITION SOY PROTEIN ISOLATE (980 ГР)")
                .price(BigDecimal.valueOf(24.75)).maker(trec).type(protein)
                .description("Soy Protein Isolate помогает поддерживать мышечную ткань, способствуя развитию спортивной фигуры. Продукт рекомендуется в качестве белковой" +
                        " добавки для применения в ежедневном рационе питания физически активных людей.")
                .url("/resources/images/prot.jpg")
                .build());
        countService.save(15, p6);

        Product p7 =productService.save(Product.builder()
                .name(" Gainer TREC NUTRITION SOLID MASS (3000 ГР)")
                .price(BigDecimal.valueOf(46.75)).maker(trec).type(gainer)
                .description("Превосходные вкусовые характеристики, отличное сочетание незаменимых и заменимых аминокислот, простые и комплексные углеводы, а также " +
                        "высококачественный, отборный концентрат белковой сыворотки выгодно отличают SOLID MASS")
//                        "специализированного спортивного питания.")
                .url("/resources/images/gein.jpg")
                .build());
        countService.save(34, p7);

        Product p8 =productService.save(Product.builder()
                .name(" Gainer TREC NUTRITION SOLID MASS (6000 ГР)")
                .price(BigDecimal.valueOf(70.75)).maker(trec).type(gainer)
                .description("Превосходные вкусовые характеристики, отличное сочетание незаменимых и заменимых аминокислот, простые и комплексные углеводы, а также " +
                        "высококачественный, отборный концентрат белковой сыворотки выгодно отличают SOLID MASS  ")
//                        "специализированного спортивного питания.")
                .url("/resources/images/gein.jpg")
                .build());
        countService.save(44, p8);

        Product p9 =productService.save(Product.builder()
                .name(" Gainer STRIMEX HARD GAIN SILVER EDITION (1500 ГР)")
                .price(BigDecimal.valueOf(15.68)).maker(strimex).type(gainer)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/gein.jpg")
                .build());
        countService.save(36, p9);

        Product p10 = productService.save(Product.builder()
                .name(" Gainer STRIMEX HARD GAIN SILVER EDITION (6000 ГР)")
                .price(BigDecimal.valueOf(50.05)).maker(strimex).type(gainer)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/gein.jpg")
                .build());
        countService.save(42, p10);

        Product p11 = productService.save(Product.builder()
                .name(" Gainer STRIMEX HARD GAIN SILVER EDITION (3000 ГР)")
                .price(BigDecimal.valueOf(20.70)).maker(strimex).type(gainer)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/gein.jpg")
                .build());
        countService.save(12, p11);

        Product p12 = productService.save(Product.builder()
                .name(" Gainer STRIMEX HARD GAIN SILVER EDITION (8000 ГР)")
                .price(BigDecimal.valueOf(60.50)).maker(strimex).type(gainer)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/gein.jpg")
                .build());
        countService.save(25, p12);

        Product p13 = productService.save(Product.builder()
                .name("Creatine TREC NUTRITION CREATINE 100% (300 ГР)")
                .price(BigDecimal.valueOf(60.50)).maker(trec).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p13);

        Product p14 =productService.save(Product.builder()
                .name("Creatine TREC NUTRITION CREATINE 100% (300 ГР)")
                .price(BigDecimal.valueOf(10.50)).maker(trec).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p14);

        Product p15 = productService.save(Product.builder()
                .name("Creatine TREC NUTRITION CREATINE 100% (600 ГР)")
                .price(BigDecimal.valueOf(18.20)).maker(trec).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p15);

        Product p16 =productService.save(Product.builder()
                .name("Creatine TREC NUTRITION CREATINE 100% (900 ГР)")
                .price(BigDecimal.valueOf(24.60)).maker(trec).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p16);

        Product p17 =productService.save(Product.builder()
                .name("Creatine TREC NUTRITION CREATINE 100% (1200 ГР)")
                .price(BigDecimal.valueOf(28.90)).maker(trec).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p17);

        Product p18 =  productService.save(Product.builder()
                .name("Creatine STRIMEX NUTRITION CREATINE 100% (300 ГР)")
                .price(BigDecimal.valueOf(6.50)).maker(strimex).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p18);

        Product p19 =productService.save(Product.builder()
                .name("Creatine STRIMEX NUTRITION CREATINE 100% (600 ГР)")
                .price(BigDecimal.valueOf(10.20)).maker(strimex).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p19);

        Product p20 = productService.save(Product.builder()
                .name("Creatine STRIMEX NUTRITION CREATINE 100% (900 ГР)")
                .price(BigDecimal.valueOf(14.60)).maker(strimex).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p20);

        Product p21 = productService.save(Product.builder()
                .name("Creatine STRIMEX NUTRITION CREATINE 100% (1200 ГР)")
                .price(BigDecimal.valueOf(18.90)).maker(strimex).type(creatine)
                .description("Hard Gain Silver Edition от Strimex выделяется среди других гейнеров благодаря списку заменимых аминокислот.  Сложные углеводы ответственны" +
                        " за постепенную загрузку энергии в мускулы. Таким образом, жировые клетки питаются меньше.")
                .url("/resources/images/creat.jpg")
                .build());
        countService.save(25, p21);

        Product p22 =productService.save(Product.builder()
                .name("СКАМЬЯ ПОД ШТАНГУ ALPIN START G-4")
                .price(BigDecimal.valueOf(100.90)).maker(alpin).type(strength_training)
                .description("Тип:многофункциональная силовая скамья с регулируемыми стойками\n" +
                        "Упражнения:жим штанги, различные упражнения с гантелями\n" +
                        "Рама:устойчивая, с однослойной покраской (38*38*1.5 мм.)\n" +
                        "Нагрузка на стойки для штанги 100 кг.")
                .url("/resources/images/skam.jpg")
                .build());
        countService.save(8, p22);

        Product p23 =productService.save(Product.builder()
                .name("СКАМЬЯ ALPIN TOP G-3")
                .price(BigDecimal.valueOf(46.20)).maker(alpin).type(strength_training)
                .description("Тип:силовая скамья под штангу и упражнений с гантелями\n" +
                        "Упражнения:жим штанги, упражнений с гантелями\n" +
                        "Рама:устойчивая, с однослойной покраской (50*50мм)\n" +
                        "Допустимая суммарная нагрузка:200 кг.\n" +
                        "Сидение:комфортабельное 120*27см (толщина 40 мм.)")
                .url("/resources/images/skam.jpg")
                .build());
        countService.save(9, p23);

        Product p24 = productService.save(Product.builder()
                .name("СКАМЬЯ ALPIN TOP G-6")
                .price(BigDecimal.valueOf(36.20)).maker(alpin).type(strength_training)
                .description("Тип:силовая скамья под штангу и упражнений с гантелями\n" +
                        "Упражнения:жим штанги, упражнений с гантелями\n" +
                        "Рама:устойчивая, с однослойной покраской (50*50мм)\n" +
                        "Допустимая суммарная нагрузка:150 кг.\n" +
                        "Сидение:комфортабельное 120*27см (толщина 40 мм.)")
                .url("/resources/images/skam.jpg")
                .build());
        countService.save(19, p24);

        Product p25 = productService.save(Product.builder()
                .name("СИЛОВАЯ СКАМЬЯ ALPIN BENCH G-10")
                .price(BigDecimal.valueOf(89.20)).maker(alpin).type(strength_training)
                .description("Тип:многофункциональная силовая скамья с партой\n" +
                        "Упражнения:жим штанги, различные упражнения с гантелями, пресс, бицепс\n" +
                        "Рама:устойчивая, с однослойной покраской (50*50)\n" +
                        "Нагрузка на парту:100 кг.\n" +
                        "Сидение:комфортабельное (40 мм.)\n")
                .url("/resources/images/skam.jpg")
                .build());
        countService.save(9, p25);

        Product p26 =productService.save(Product.builder()
                .name("СИЛОВАЯ СКАМЬЯ ALPIN BENCH G-12")
                .price(BigDecimal.valueOf(96.20)).maker(alpin).type(strength_training)
                .description("Тип:многофункциональная силовая скамья с партой\n" +
                        "Упражнения:жим штанги, различные упражнения с гантелями, пресс, бицепс\n" +
                        "Рама:устойчивая, с однослойной покраской (50*50)\n" +
                        "Нагрузка на парту:190 кг.\n" +
                        "Сидение:комфортабельное (40 мм.)\n")
                .url("/resources/images/skam.jpg")
                .build());
        countService.save(25, p26);

        Product p27 =productService.save(Product.builder()
                .name("ВЕЛОТРЕНАЖЕР ALPIN PICCO B-180 WHITE")
                .price(BigDecimal.valueOf(206.20)).maker(alpin).type(exercise_bike)
                .description("Система нагружения:Магнитная\n" +
                        "Масса маховика:6 кг  сбалансированный\n" +
                        "Регулировка нагрузки:Механическая 8 уровней\n" +
                        "Размеры в рабочем состоянии (дл х шир х выс), см:100x49x125\n" +
                        "Вес, кг:21\n")
                .url("/resources/images/vel.jpg")
                .build());
        countService.save(25, p27);

        Product p28 =productService.save(Product.builder()
                .name("ВЕЛОТРЕНАЖЕР ALPIN GROSS B-190 WHITE")
                .price(BigDecimal.valueOf(310.20)).maker(alpin).type(exercise_bike)
                .description("Система нагружения:Магнитная\n" +
                        "Масса маховика:6 кг  сбалансированный\n" +
                        "Регулировка нагрузки:Механическая 8 уровней\n" +
                        "Размеры в рабочем состоянии (дл х шир х выс), см:100x49x125\n" +
                        "Вес, кг:16\n")
                .url("/resources/images/vel.jpg")
                .build());
        countService.save(10, p28);

        Product p29 = productService.save(Product.builder()
                .name("ВЕЛОТРЕНАЖЕР ALPIN OPTIMAL B175")
                .price(BigDecimal.valueOf(127.30)).maker(alpin).type(exercise_bike)
                .description("Система нагружения:Магнитная\n" +
                        "Масса маховика:6 кг  сбалансированный\n" +
                        "Регулировка нагрузки:Механическая 8 уровней\n" +
                        "Размеры в рабочем состоянии (дл х шир х выс), см:100x49x125\n" +
                        "Вес, кг:16\n")
                .url("/resources/images/vel.jpg")
                .build());
        countService.save(15, p29);

        Product p30 = productService.save(Product.builder()
                .name("ВЕЛОТРЕНАЖЕР ALPIN ACTUEL B-160 КРАСНЫЙ")
                .price(BigDecimal.valueOf(95.80)).maker(alpin).type(exercise_bike)
                .description("Система нагружения:Магнитная\n" +
                        "Масса маховика:6 кг  сбалансированный\n" +
                        "Регулировка нагрузки:Механическая 8 уровней\n" +
                        "Размеры в рабочем состоянии (дл х шир х выс), см:100x49x125\n" +
                        "Вес, кг:16\n")
                .url("/resources/images/vel.jpg")
                .build());
        countService.save(15, p30);

        Product p31 =  productService.save(Product.builder()
                .name("ВЕЛОТРЕНАЖЕР ALPIN GROSS B-190")
                .price(BigDecimal.valueOf(310.10)).maker(alpin).type(exercise_bike)
                .description("Система нагружения:Магнитная\n" +
                        "Масса маховика:6 кг  сбалансированный\n" +
                        "Регулировка нагрузки:Механическая 8 уровней\n" +
                        "Размеры в рабочем состоянии (дл х шир х выс), см:100x49x125\n" +
                        "Вес, кг:16\n")
                .url("/resources/images/vel.jpg")
                .build());
        countService.save(15, p31);

        Product p32 = productService.save(Product.builder()
                .name("ВЕЛОТРЕНАЖЕР ALPIN PICCO B-182R")
                .price(BigDecimal.valueOf(238.80)).maker(alpin).type(exercise_bike)
                .description("Система нагружения:Магнитная\n" +
                        "Масса маховика:6 кг  сбалансированный\n" +
                        "Регулировка нагрузки:Механическая 8 уровней\n" +
                        "Размеры в рабочем состоянии (дл х шир х выс), см:100x49x125\n" +
                        "Вес, кг:16\n")
                .url("/resources/images/vel.jpg")
                .build());
        countService.save(15, p32);

        Product p33 = productService.save(Product.builder()
                .name("БЕГОВАЯ ДОРОЖКА ALPIN SHAKE T-169")
                .price(BigDecimal.valueOf(315.45)).maker(alpin).type(treadmills)
                .description("Тип:Магнитная/Механическая\n" +
                        "Размеры бегового полотна:1170*340 мм. толщина 1,8 мм.\n" +
                        "Толщина деки:16 мм\n" +
                        "Размеры в сложенном состоянии (дл х шир х выс), см:900х640х1400 мм.\n" +
                        "Максимальный вес пользователя:100 кг")
                .url("/resources/images/beg.jpg")
                .build());
        countService.save(15, p33);

        Product p34 = productService.save(Product.builder()
                .name("БЕГОВАЯ ДОРОЖКА ALPIN REISE T-179")
                .price(BigDecimal.valueOf(490.25)).maker(alpin).type(treadmills)
                .description("Тип:Магнитная/Механическая\n" +
                        "Размеры бегового полотна:1170*340 мм. толщина 1,8 мм.\n" +
                        "Толщина деки:16 мм\n" +
                        "Размеры в сложенном состоянии (дл х шир х выс), см:900х640х1400 мм.\n" +
                        "Максимальный вес пользователя:100 кг")
                .url("/resources/images/beg.jpg")
                .build());
        countService.save(15, p34);

        Product p35 = productService.save(Product.builder()
                .name("БЕГОВАЯ ДОРОЖКА ALPIN GLACE T-177")
                .price(BigDecimal.valueOf(395.45)).maker(alpin).type(treadmills)
                .description("Тип:Магнитная/Механическая\n" +
                        "Размеры бегового полотна:1170*340 мм. толщина 1,8 мм.\n" +
                        "Толщина деки:16 мм\n" +
                        "Размеры в сложенном состоянии (дл х шир х выс), см:900х640х1400 мм.\n" +
                        "Максимальный вес пользователя:100 кг")
                .url("/resources/images/beg.jpg")
                .build());
        countService.save(15, p35);

        Product p36 =productService.save(Product.builder()
                .name("БЕГОВАЯ ДОРОЖКА ALPIN LAUFEN T-190")
                .price(BigDecimal.valueOf(840.40)).maker(alpin).type(treadmills)
                .description("Тип:Магнитная/Механическая\n" +
                        "Размеры бегового полотна:1170*340 мм. толщина 1,8 мм.\n" +
                        "Толщина деки:16 мм\n" +
                        "Размеры в сложенном состоянии (дл х шир х выс), см:900х640х1400 мм.\n" +
                        "Максимальный вес пользователя:100 кг")
                .url("/resources/images/beg.jpg")
                .build());
        countService.save(15, p36);

        Product p37 = productService.save(Product.builder()
                .name("БЕГОВАЯ ДОРОЖКА ALPIN MARATHON T-180")
                .price(BigDecimal.valueOf(490.90)).maker(alpin).type(treadmills)
                .description("Тип:Магнитная/Механическая\n" +
                        "Размеры бегового полотна:1170*340 мм. толщина 1,8 мм.\n" +
                        "Толщина деки:16 мм\n" +
                        "Размеры в сложенном состоянии (дл х шир х выс), см:900х640х1400 мм.\n" +
                        "Максимальный вес пользователя:100 кг")
                .url("/resources/images/beg.jpg")
                .build());
        countService.save(15, p37);

        Product p38 = productService.save(Product.builder()
                .name("БЕГОВАЯ ДОРОЖКА ALPIN ROUTE T-195")
                .price(BigDecimal.valueOf(645.95)).maker(alpin).type(treadmills)
                .description("Тип:Магнитная/Механическая\n" +
                        "Размеры бегового полотна:1170*340 мм. толщина 1,8 мм.\n" +
                        "Толщина деки:16 мм\n" +
                        "Размеры в сложенном состоянии (дл х шир х выс), см:900х640х1400 мм.\n" +
                        "Максимальный вес пользователя:100 кг")
                .url("/resources/images/beg.jpg")
                .build());
        countService.save(15, p38);

        Product p39 = productService.save(Product.builder()
                .name("БЕГОВАЯ ДОРОЖКА ALPIN MARATHON T-181")
                .price(BigDecimal.valueOf(645.45)).maker(alpin).type(treadmills)
                .description("Тип:Магнитная/Механическая\n" +
                        "Размеры бегового полотна:1170*340 мм. толщина 1,8 мм.\n" +
                        "Толщина деки:16 мм\n" +
                        "Размеры в сложенном состоянии (дл х шир х выс), см:900х640х1400 мм.\n" +
                        "Максимальный вес пользователя:100 кг")
                .url("/resources/images/beg.jpg")
                .build());
        countService.save(55, p39);

    }
}




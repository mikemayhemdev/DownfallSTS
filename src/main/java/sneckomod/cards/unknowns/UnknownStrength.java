package sneckomod.cards.unknowns;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.Keyword;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

@NoCompendium
public class UnknownStrength extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownStrength");
    public static boolean bruh = false;

    public UnknownStrength() {
        super(ID, CardType.SKILL, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "UnknownStrength.png");
    }

    public boolean useCheck(AbstractCard card) {
        bruh = false;

        Keyword keywordString = CardCrawlGame.languagePack.getKeywordString("Game Dictionary").STRENGTH;
        for (int i = 0; i < keywordString.NAMES.length; i++) {
            if (!bruh) {
                String key = keywordString.NAMES[i];
                key = key.toLowerCase();
                String test = card.rawDescription.toLowerCase();
                bruh = (test.contains(" " + key + " ") || test.contains(" " + key + ",") || test.contains(" " + key + ".") || test.contains(" " + key + "。"));
            }
        }     //SlimeboundMod.logger.info("Strength U checks: keywordString: " + keywordString);
        //SlimeboundMod.logger.info("Strength U checks: key: " + key);
        //SlimeboundMod.logger.info("bruh " + bruh);

        /*
        try {
            ClassPool pool = Loader.getClassPool();
            CtClass ctClass = pool.get(card.getClass().getName());
            ctClass.defrost();
            CtMethod useMethod;
            try {
                useMethod = ctClass.getDeclaredMethod("use");
            } catch (NotFoundException ignore) {
                // This card doesn't have a `use` method, skip it
                return false;
            }

            useMethod.instrument(new ExprEditor() {
                @Override
                public void edit(NewExpr e) {
                    try {
                        CtConstructor ctConstructor = e.getConstructor();
                        CtClass cls = ctConstructor.getDeclaringClass();
                        if (cls != null) {
                            CtClass parent = cls;
                            do {
                                parent = parent.getSuperclass();
                            } while (parent != null && !parent.getName().equals(AbstractPower.class.getName()));
                            if (parent != null && parent.getName().equals(AbstractPower.class.getName())) {
                                if (ctConstructor.getDeclaringClass().getName().equals(StrengthPower.class.getName()))
                                    bruh = true;
                            }
                        }
                    } catch (NotFoundException ignored) {
                    }
                }
            });
        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }*/
        return bruh;
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return this::useCheck;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownStrengthReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerStrength;
    }

}
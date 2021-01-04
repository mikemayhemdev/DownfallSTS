package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownDexterity extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownDexterity");
    public static boolean bruh = false;

    public UnknownDexterity() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON);
    }

    public boolean useCheck(AbstractCard card) {
        bruh = false;

        Keyword keywordString = CardCrawlGame.languagePack.getKeywordString("Game Dictionary").DEXTERITY;
        for (int i = 0; i < keywordString.NAMES.length; i++) {
            if (!bruh){
                String key = keywordString.NAMES[i];
                key = key.toLowerCase();
                String test = card.rawDescription.toLowerCase();
                bruh = (test.contains(" " + key + " ") || test.contains(" " + key + ",") || test.contains(" " + key + ".") || test.contains(" " + key + "。"));
            }
        }
        //SlimeboundMod.logger.info("Strength U checks: keywordString: " + keywordString);
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
                                if (ctConstructor.getDeclaringClass().getName().equals(DexterityPower.class.getName()))
                                    bruh = true;
                            }
                        }
                    } catch (NotFoundException ignored) {
                    }
                }
            });
        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
        */
        return bruh;
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return this::useCheck;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownDexterityReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerDex;
    }
}
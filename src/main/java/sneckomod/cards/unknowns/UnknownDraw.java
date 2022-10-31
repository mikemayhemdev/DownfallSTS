package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownDraw extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownDraw");
    public static boolean bruh = false;

    public UnknownDraw() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }

    public boolean useCheck(AbstractCard card) {
        bruh = false;

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
                            } while (parent != null && !parent.getName().equals(AbstractGameAction.class.getName()) && !parent.getName().equals(AbstractPower.class.getName()));
                            if (parent != null && (parent.getName().equals(AbstractGameAction.class.getName()) || !parent.getName().equals(AbstractPower.class.getName()))) {
                                if (ctConstructor.getDeclaringClass().getName().equals(DrawCardAction.class.getName()) || ctConstructor.getDeclaringClass().getName().equals(DrawCardNextTurnPower.class.getName()))
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
        return bruh;
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return this::useCheck;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownDrawReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerDraw;
    }
}
package sneckomod.util;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.utility.ConditionalDrawAction;
import com.megacrit.cardcrawl.actions.watcher.CollectAction;
import com.megacrit.cardcrawl.actions.watcher.FollowUpAction;
import com.megacrit.cardcrawl.actions.watcher.InnerPeaceAction;
import com.megacrit.cardcrawl.actions.watcher.SanctityAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.MachineLearning;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.cards.green.Tactician;
import com.megacrit.cardcrawl.cards.purple.DeusExMachina;
import com.megacrit.cardcrawl.cards.red.Sentinel;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.DevaPower;
import com.megacrit.cardcrawl.powers.watcher.RushdownPower;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.NewExpr;

import java.util.Arrays;


//This code is Mistress Autumn's, not mine!

public class ArchetypeHelper {
    public static Class<?>[] energyShenaniganCards = {Sentinel.class, Tactician.class, DeusExMachina.class};
    public static Class<?>[] energyClasses = {GainEnergyAction.class, EnergizedPower.class, EnergizedBluePower.class, Plasma.class, BerserkPower.class, DropkickAction.class, DoppelgangerAction.class, HeelHookAction.class, GainEnergyIfDiscardAction.class, AggregateEnergyAction.class, DoubleEnergyAction.class, FissionAction.class, RecycleAction.class, SunderAction.class, CollectAction.class, CollectPower.class, FollowUpAction.class, DevaPower.class};
    public static Class<?>[] drawShenaniganCards = {Reflex.class};
    public static Class<?>[] drawClasses = {DrawCardAction.class, DrawCardNextTurnPower.class, ConditionalDrawAction.class, DarkEmbracePower.class, DropkickAction.class, EvolvePower.class, BrutalityPower.class, CalculatedGambleAction.class, DoppelgangerAction.class, ExpertiseAction.class, ToolsOfTheTradePower.class, CompileDriverAction.class, FTLAction.class, HeatsinkPower.class, FissionAction.class, MachineLearning.class, InnerPeaceAction.class, RushdownPower.class, SanctityAction.class};
    public static String[] orbCodes = {"[E]","[R]","[B]","[G]","[W]"};

    public static boolean hasBlockKeyword(AbstractCard card) {
        return Arrays.stream(GameDictionary.BLOCK.NAMES).anyMatch(s -> card.keywords.contains(s));
    }

    public static boolean givesEnergy(AbstractCard card) {
        if (Arrays.stream(orbCodes).anyMatch(s -> card.rawDescription.contains(s))) {
            return true;
        }
        //TODO should probably also check if it actually spawns the card
        if (card.cardsToPreview instanceof Miracle) {
            return true;
        }
        //More annoying cases for matching are hardcoded, like Sentinel
        if (Arrays.stream(energyShenaniganCards).anyMatch(clz -> clz.getName().equals(card.getClass().getName()))) {
            return true;
        }
        final boolean[] foundEnergy = {false};
        try {
            //Grab the use method
            ClassPool pool = Loader.getClassPool();
            CtClass ctClass = pool.get(card.getClass().getName());
            ctClass.defrost();
            CtMethod useMethod;
            try {
                useMethod = ctClass.getDeclaredMethod("use");
            } catch (NotFoundException ignore) {
                // This card doesn't have a `use` method, skip it
                // I blame Infinite Spire
                return false;
            }

            useMethod.instrument(new ExprEditor() {
                @Override
                public void edit(NewExpr n) {
                    try {
                        //Check if the new object happens to match one of our energy related classes
                        CtConstructor constructor = n.getConstructor();
                        CtClass activeClass = constructor.getDeclaringClass();

                        if (activeClass != null) {
                            CtClass[] plz = {activeClass};
                            //Loop until we either run out of supers or we find a matching class
                            while (activeClass != null && (Arrays.stream(energyClasses).noneMatch(clz -> clz.getName().equals(plz[0].getName())))) {
                                activeClass = activeClass.getSuperclass();
                                plz[0] = activeClass;
                            }
                            //We found it, nice
                            if (activeClass != null && (Arrays.stream(energyClasses).anyMatch(clz -> clz.getName().equals(plz[0].getName())))) {
                                foundEnergy[0] = true;
                            }
                        }
                    } catch (Exception ignored) {}
                }
            });
        } catch(Exception ignored) {}
        return foundEnergy[0];
    }

    public static boolean drawsCards(AbstractCard card) {
        //More annoying cases for matching are hardcoded, like Reflex
        if (Arrays.stream(drawShenaniganCards).anyMatch(clz -> clz.getName().equals(card.getClass().getName()))) {
            return true;
        }
        final boolean[] foundDrawCard = {false};
        try {
            //Grab the use method
            ClassPool pool = Loader.getClassPool();
            CtClass ctClass = pool.get(card.getClass().getName());
            ctClass.defrost();
            CtMethod useMethod;
            try {
                useMethod = ctClass.getDeclaredMethod("use");
            } catch (NotFoundException ignore) {
                // This card doesn't have a `use` method, skip it
                // I blame Infinite Spire
                return false;
            }

            useMethod.instrument(new ExprEditor() {
                @Override
                public void edit(NewExpr n) {
                    try {
                        //Check if the new object extends DrawCardAction or DrawCardNextTurnPower
                        CtConstructor constructor = n.getConstructor();
                        CtClass activeClass = constructor.getDeclaringClass();

                        if (activeClass != null) {
                            CtClass[] plz = {activeClass};
                            //Loop until we either run out of supers or we find a matching class
                            while (activeClass != null && (Arrays.stream(drawClasses).noneMatch(clz -> clz.getName().equals(plz[0].getName())))) {
                                activeClass = activeClass.getSuperclass();
                                plz[0] = activeClass;
                            }
                            //We found it, nice
                            if (activeClass != null && (Arrays.stream(drawClasses).anyMatch(clz -> clz.getName().equals(plz[0].getName())))) {
                                foundDrawCard[0] = true;
                            }
                        }
                    } catch (Exception ignored) {}
                }
            });
        } catch(Exception ignored) {}
        return foundDrawCard[0];
    }

    public static boolean appliesDebuff(AbstractCard card) {
        //Set up some flags
        final boolean[] foundDebuff = {false};
        final boolean[] foundBuff = {false};
        final boolean[] isDebuff = {false};
        try {
            //Grab the use method
            ClassPool pool = Loader.getClassPool();
            CtClass ctClass = pool.get(card.getClass().getName());
            ctClass.defrost();
            CtMethod useMethod;
            try {
                useMethod = ctClass.getDeclaredMethod("use");
            } catch (NotFoundException ignore) {
                // This card doesn't have a `use` method, skip it
                // I blame Infinite Spire
                return false;
            }

            useMethod.instrument(new ExprEditor() {
                @Override
                public void edit(NewExpr n) {
                    try {
                        //Check if the new object extends AbstractPower
                        CtConstructor constructor = n.getConstructor();
                        CtClass originalClass = constructor.getDeclaringClass();

                        if (originalClass != null) {
                            CtClass currentClass = originalClass;
                            while (currentClass != null && !currentClass.getName().equals(AbstractPower.class.getName())) {
                                currentClass = currentClass.getSuperclass();
                            }
                            //We found AbstractPower, good to go
                            if (currentClass != null && currentClass.getName().equals(AbstractPower.class.getName())) {
                                //Define a checker for finding the power type
                                ExprEditor debuffChecker = new ExprEditor() {
                                    @Override
                                    public void edit(FieldAccess f) {
                                        if (f.getClassName().equals(AbstractPower.PowerType.class.getName())) {
                                            if (f.getFieldName().equals("DEBUFF")) {
                                                foundDebuff[0] = true;
                                            }
                                            if (f.getClassName().equals("BUFF")) {
                                                foundBuff[0] = true;
                                            }
                                        }
                                    }
                                };

                                //Check both the constructor and the updateDescription to catch things like Strength
                                constructor.instrument(debuffChecker);
                                CtMethod descriptionMethod = currentClass.getDeclaredMethod("updateDescription");
                                descriptionMethod.instrument(debuffChecker);

                                //If we actually found a debuff
                                if (foundDebuff[0]) {
                                    //Check if it also isn't a buff sometimes
                                    if (!foundBuff[0]) {
                                        isDebuff[0] = true;
                                    } else {
                                        //Guess based on the card target
                                        if (card.target == AbstractCard.CardTarget.ENEMY || card.target == AbstractCard.CardTarget.ALL_ENEMY) {
                                            isDebuff[0] = true;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                }
            });
        } catch (Exception ignored) {}
        return isDebuff[0];
    }
}
package downfall.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.dynamicvariables.BlockVariable;
import basemod.helpers.dynamicvariables.DamageVariable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderCustomDynamicVariableCN;
import champ.ChampChar;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.*;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import javassist.*;
import javassist.bytecode.DuplicateMemberException;

import java.util.Objects;
import java.util.regex.Pattern;

import static downfall.patches.EvilModeCharacterSelect.evilMode;

//TODO: I accidentally pushed one of my attempt code to change those evil run specific relic names(and image) for the related relics, it's not fully working
// (breaks when you load a game) but shouldn't cause any bugs I think, Mwalls

public class RelicOverrides {

//    @SpirePatch(
//            clz = SlaversCollar.class,
//            method = "setDescription"
//    )
//    public static class slaversCollarDesc {
//        @SpirePrefixPatch
//        public static SpireReturn<String> Prefix() {
//            if (EvilModeCharacterSelect.evilMode) {
//                return SpireReturn.Return(CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[0]);
//
//            }
//            return SpireReturn.Continue();
//        }
//
//    }

//    @SpirePatch(
//            clz = PreservedInsect.class,
//            method = "canSpawn"
//    )
//    public static class PreservedOverwrite {
//        @SpirePrefixPatch
//            public boolean canSpawn() {
//                return Settings.isEndless || (AbstractDungeon.floorNum <= 52 && !evilMode) || (AbstractDungeon.floorNum <= 48 && evilMode);
//            }
//    }


//    // thanks sandtag for the code
//      @SpirePatch2(clz = PreservedInsect.class, method = "<ctor>")
//      public static class AddNewMethodPI {
//     @SpireRawPatch
//        public static void addMethod(CtBehavior ctMethodToPatch) throws CannotCompileException, NotFoundException {
//         CtClass ctNestClass = ctMethodToPatch.getDeclaringClass();
//         CtClass superClass = ctNestClass.getSuperclass();
//         CtMethod superMethod = superClass.getDeclaredMethod("canSpawn");
//         CtMethod updateMethod = CtNewMethod.delegator(superMethod, ctNestClass);
//                try {
//                    ctNestClass.addMethod(updateMethod);
//          } catch (DuplicateMemberException ignored) {
//                    updateMethod = ctNestClass.getDeclaredMethod("canSpawn");
//          }
//          updateMethod.insertBefore("if(true){return " + PreservedInsect.class.getName() + ".addCanSpawn48PI($0);}");
//        }
//      }
//
//    public static boolean addCanSpawn48PI(PreservedInsect __instance) {
//        return (Settings.isEndless || (AbstractDungeon.floorNum <= 52 && !evilMode) || (AbstractDungeon.floorNum <= 48 && evilMode));
//    }


    @SpirePatch(clz = BustedCrown.class, method = "getUpdatedDescription")
    public static class BustedCrownJokeText {
        @SpirePrefixPatch
        public static void Prefix(BustedCrown _instance) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass == ChampChar.Enums.THE_CHAMP) {
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:BustedCrownGagText").FLAVOR;
            }
        }
    }


    @SpirePatch(
            clz = OldCoin.class,
            method = "getUpdatedDescription"
    )
    public static class oldCoinName {
        @SpirePrefixPatch
        public static void Prefix(OldCoin _instance) {
            if (evilMode && _instance.name != CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[1]) {
                ReflectionHacks.setPrivateStaticFinal(OldCoin.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[1]);
                _instance.img = TextureLoader.getTexture(downfallMod.assetPath("images/relics/oldCoinEvil.png"));
                _instance.outlineImg = TextureLoader.getTexture(downfallMod.assetPath("images/relics/Outline/oldCoinEvil.png"));
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[2];
            }

        }

    }

//    @SpirePatch(
//            clz = BlueCandle.class,
//            method = "onUseCard"
//    )
//    public static class BlueCandleOverride {
//       // @SpirePatch
//        public static void onUseCard(BlueCandle __instance, AbstractCard card, UseCardAction action) {
//            if (card.type == AbstractCard.CardType.CURSE) {
//                __instance.flash();
//                if (!EvilModeCharacterSelect.evilMode || card.cost == -2) {
//                    AbstractDungeon.actionManager.addToBottom(
//                            new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1, AbstractGameAction.AttackEffect.FIRE)
//                    );
//                }
//                card.exhaust = true;
//                action.exhaustCard = true;
//            }
//        }
//    }
//
//    @SpirePatch(
//            clz = BlueCandle.class,
//            method = "getUpdatedDescription"
//    )
//    public static class bluecandleName {
//            @SpirePrefixPatch
//            public static void Prefix(BlueCandle _instance) {
//                if (EvilModeCharacterSelect.evilMode) {
//                    _instance.imgUrl = null;
//                    ReflectionHacks.setPrivateStaticFinal(MembershipCard.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:BlackCandle").DESCRIPTIONS[1]);_instance.img = TextureLoader.getTexture(downfallMod.assetPath("images/relics/BlackCandle.png"));
//                    _instance.outlineImg = TextureLoader.getTexture(downfallMod.assetPath("images/relics/Outline/BlackCandleOutline.png"));
//                    _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:BlackCandle").FLAVOR;
//                }
//
//            }
//        }
//
//
//
//    @SpirePatch(
//            clz = BlueCandle.class,
//            method = "setDescription"
//    )
//    public static class BlueCandleDesc {
//        @SpirePrefixPatch
//        public static SpireReturn<String> Prefix() {
//
//            if (EvilModeCharacterSelect.evilMode) {
//                return SpireReturn.Return(CardCrawlGame.languagePack.getRelicStrings("downfall:BlackCandle").DESCRIPTIONS[1]);
//            }
//
//            return SpireReturn.Continue();
//        }
//    }


    @SpirePatch(
            clz = MembershipCard.class,
            method = "getUpdatedDescription"
    )
    public static class membershipCardName {
        @SpirePrefixPatch
        public static void Prefix(MembershipCard _instance) {
            if (evilMode && _instance.name != CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[3]) {
                ReflectionHacks.setPrivateStaticFinal(MembershipCard.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[3]);
                _instance.img = TextureLoader.getTexture(downfallMod.assetPath("images/relics/membershipCardEvil.png"));
                _instance.outlineImg = TextureLoader.getTexture(downfallMod.assetPath("images/relics/Outline/membershipCardEvil.png"));
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[4];

            }

        }
    }


    @SpirePatch(
            clz = Courier.class,
            method = "getUpdatedDescription"
    )
    public static class courierName {
        @SpirePrefixPatch
        public static void Prefix(Courier _instance) {
            if (evilMode && _instance.name != CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[6]) {
                ReflectionHacks.setPrivateStaticFinal(Courier.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[6]);
                _instance.imgUrl = null;
                _instance.img = TextureLoader.getTexture(downfallMod.assetPath("images/relics/courierEvil.png"));
                _instance.outlineImg = TextureLoader.getTexture(downfallMod.assetPath("images/relics/Outline/courierEvil.png"));
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[5];

            }

        }
    }

    @SpirePatch(
            clz = PrismaticShard.class,
            method = "getUpdatedDescription"
    )
    public static class prismaticDesc {
        @SpirePrefixPatch
        public static void Postfix(PrismaticShard _instance) {
            if (evilMode) {
                //ReflectionHacks.setPrivateStaticFinal(Courier.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[6]);
               // _instance.description = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[8];

            }

        }
    }

    @SpirePatch(
            clz = Ectoplasm.class,
            method = "getUpdatedDescription"
    )
    public static class EctoImage {
        @SpirePrefixPatch
        public static void Prefix(Ectoplasm _instance) {
            if (evilMode) {
                _instance.imgUrl = null;
                ReflectionHacks.setPrivateStaticFinal(Ectoplasm.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:Hecktoplasm").DESCRIPTIONS[1]);
                _instance.img = TextureLoader.getTexture(downfallMod.assetPath("images/relics/ectoplasmEvil.png"));
                _instance.outlineImg = TextureLoader.getTexture(downfallMod.assetPath("images/relics/Outline/ectoplasmEvil.png"));
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:Hecktoplasm").FLAVOR;
            }

        }
    }

    @SpirePatch(
            clz = Ectoplasm.class,
            method = "setDescription"
    )
    public static class EctoDesc {
        @SpirePrefixPatch
        public static SpireReturn<String> Prefix() {

            if (evilMode) {
                return SpireReturn.Return(CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[9]);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=AbstractRelic.class,
            method= SpirePatch.CONSTRUCTOR
    )
    public static class EctoTitle {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractRelic __instance,String setId, String imgName, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
            if(Objects.equals(__instance.relicId, "Ectoplasm") && evilMode) {
                ReflectionHacks.setPrivateFinal(__instance, AbstractRelic.class, "relicStrings", CardCrawlGame.languagePack.getRelicStrings("downfall:Hecktoplasm").NAME);

            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ImageMaster.class, "loadRelicImg");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
/*
    @SpirePatch(
            clz = Courier.class,
            method = "setDescription"
    )
    public static class courierCollarDesc {
        @SpirePrefixPatch
        public static SpireReturn<String> Prefix() {
            if (EvilModeCharacterSelect.evilMode) {
                return SpireReturn.Return(CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[7]);

            }
            return SpireReturn.Continue();
        }

    }
    */


}


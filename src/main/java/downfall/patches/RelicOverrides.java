package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.*;
import downfall.downfallMod;

public class RelicOverrides {

    @SpirePatch(
            clz = SlaversCollar.class,
            method = "setDescription"
    )
    public static class slaversCollarDesc {
        @SpirePrefixPatch
        public static SpireReturn<String> Prefix() {
            if (EvilModeCharacterSelect.evilMode) {
                return SpireReturn.Return(CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[0]);

            }
            return SpireReturn.Continue();
        }

    }


    @SpirePatch(
            clz = OldCoin.class,
            method = "getUpdatedDescription"
    )
    public static class oldCoinName {
        @SpirePrefixPatch
        public static void Prefix(OldCoin _instance) {
            if (EvilModeCharacterSelect.evilMode && _instance.name != CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[1]) {
                //ReflectionHacks.setPrivateStaticFinal(OldCoin.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[1]);
                _instance.img = ImageMaster.loadImage(downfallMod.assetPath("images/relics/oldCoinEvil.png"));
                _instance.outlineImg = ImageMaster.loadImage(downfallMod.assetPath("images/relics/Outline/oldCoinEvil.png"));
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[2];
            }

        }

    }


    @SpirePatch(
            clz = MembershipCard.class,
            method = "getUpdatedDescription"
    )
    public static class membershipCardName {
        @SpirePrefixPatch
        public static void Prefix(MembershipCard _instance) {
            if (EvilModeCharacterSelect.evilMode && _instance.name != CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[3]) {
                //ReflectionHacks.setPrivateStaticFinal(MembershipCard.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[3]);
                _instance.img = ImageMaster.loadImage(downfallMod.assetPath("images/relics/membershipCardEvil.png"));
                _instance.outlineImg = ImageMaster.loadImage(downfallMod.assetPath("images/relics/Outline/membershipCardEvil.png"));
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
            if (EvilModeCharacterSelect.evilMode && _instance.name != CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[6]) {
                //ReflectionHacks.setPrivateStaticFinal(Courier.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[6]);
                _instance.imgUrl = null;
                _instance.img = ImageMaster.loadImage(downfallMod.assetPath("images/relics/courierEvil.png"));
                _instance.outlineImg = ImageMaster.loadImage(downfallMod.assetPath("images/relics/Outline/courierEvil.png"));
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
            if (EvilModeCharacterSelect.evilMode) {
                //ReflectionHacks.setPrivateStaticFinal(Courier.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[6]);
                _instance.description = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[8];

            }

        }
    }

    @SpirePatch(
            clz = Ectoplasm.class,
            method = "getUpdatedDescription"
    )
    public static class ectoDesc {
        @SpirePrefixPatch
        public static void Prefix(Ectoplasm _instance) {
            if (EvilModeCharacterSelect.evilMode) {
                //ReflectionHacks.setPrivateStaticFinal(Courier.class, "name", CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[6]);
                _instance.imgUrl = null;
                _instance.img = ImageMaster.loadImage(downfallMod.assetPath("images/relics/ectoplasmEvil.png"));
                _instance.outlineImg = ImageMaster.loadImage(downfallMod.assetPath("images/relics/Outline/ectoplasmEvil.png"));
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[9];
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


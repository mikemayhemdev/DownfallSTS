package downfall.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.GameDataStringBuilder;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import downfall.downfallMod;
import downfall.rooms.HeartShopRoom;

import java.util.*;

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
                _instance.img = new Texture(downfallMod.assetPath("images/relics/oldCoinEvil.png"));
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
                _instance.img = new Texture(downfallMod.assetPath("images/relics/membershipCardEvil.png"));
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
                _instance.img = new Texture(downfallMod.assetPath("images/relics/courierEvil.png"));
                _instance.flavorText = CardCrawlGame.languagePack.getRelicStrings("downfall:replacements").DESCRIPTIONS[5];

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


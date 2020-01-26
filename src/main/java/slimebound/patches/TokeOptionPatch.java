package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.TokeOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;
import slimebound.SlimeboundMod;
import slimebound.relics.ScrapOozeRelic;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.*;

@SpirePatch(clz= TokeOption.class,method="useOption")
public class TokeOptionPatch {


    public static void Prefix() {


        SlimeboundMod.scrapping = false;

    }
}


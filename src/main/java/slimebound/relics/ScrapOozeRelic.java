package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import slimebound.actions.BuffPikeStrengthAction;

import slimebound.characters.SlimeboundCharacter;
import slimebound.ui.ScrapBonfireOption;

import java.util.ArrayList;

public class ScrapOozeRelic extends CustomRelic {
    public static final String ID = "Slimebound:ScrapOozeRelic";
    public static final String IMG_PATH = "relics/scrapOoze.png";
    public static final String OUTLINE_IMG_PATH = "relics/scrapOozeOutline.png";

    public ScrapOozeRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter = 6;
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new ScrapBonfireOption(!AbstractDungeon.player.masterDeck.getPurgeableCards().isEmpty()));
    }

    public String getUpdatedDescription() {
        return (this.DESCRIPTIONS[0]);
    }

    public void atBattleStartPreDraw() {
        this.flash();
        addToBot(new BuffPikeStrengthAction(counter));
       // AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.ScrapOozeSlime(), false, false));

    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }

    public void incrementScrapNum(int amount) {
        if (amount != 0) {
            this.counter += amount;
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new ScrapOozeRelic();
    }

}
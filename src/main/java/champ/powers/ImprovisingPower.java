package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.stances.AbstractChampStance;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.util.TextureLoader;
import sneckomod.powers.UnendingSupplyPower;

public class ImprovisingPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ChampMod.makeID("ImprovisingPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Improvising84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Improvising32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ImprovisingPower(final int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.owner = AbstractDungeon.player;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!(AbstractDungeon.player.stance instanceof NeutralStance)) {

            for (int i = 0; i < this.amount; ++i) {
                if (AbstractDungeon.player.stance instanceof AbstractChampStance)
                    ((AbstractChampStance) AbstractDungeon.player.stance).techique();
            }

            if (AbstractDungeon.player.stance instanceof NeutralStance) {
                if (AbstractDungeon.cardRandomRng.randomBoolean()) {
                    addToBot(new ChangeStanceAction(new BerserkerStance()));
                } else {
                    addToBot(new ChangeStanceAction(new DefensiveStance()));
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        }
        if (this.amount != 1) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

        @Override
        public AbstractPower makeCopy () {
            return new ImprovisingPower(this.amount);
        }
    }
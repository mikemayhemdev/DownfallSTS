package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import champ.stances.UltimateStance;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

import java.util.ArrayList;

public class DancingMasterPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("DancingMasterPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/DancingMaster84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/DancingMaster32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DancingMasterPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    private ArrayList<String> stancesEnteredThisTurn = new ArrayList<>();
    private boolean usedYet = false;

    @Override
    public void atStartOfTurn() {
        stancesEnteredThisTurn.clear();
        usedYet = false;
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!newStance.ID.equals(NeutralStance.STANCE_ID)) {
            if (!stancesEnteredThisTurn.contains(newStance.ID))
                stancesEnteredThisTurn.add(newStance.ID);
            if (stancesEnteredThisTurn.size() == 3 && !usedYet) {
                flash();
                addToBot(new GainEnergyAction(amount));
                addToBot(new DrawCardAction(2));
                usedYet = true;
            }
        }
    }

    String getStancesCorrectly() {
        String s = "";
        if (stancesEnteredThisTurn.contains(DefensiveStance.STANCE_ID)) {
            s += " #bDefensive ";
        }
        if (stancesEnteredThisTurn.contains(GladiatorStance.STANCE_ID)) {
            s += " #yGladiator ";
        }
        if (stancesEnteredThisTurn.contains(BerserkerStance.STANCE_ID)) {
            s += " #rBerserker ";
        }
        if (stancesEnteredThisTurn.contains(UltimateStance.STANCE_ID)) {
            s += " #gUltimate ";
        }
        if (stancesEnteredThisTurn.contains(WrathStance.STANCE_ID)) {
            s += " #rWrath ";
        }
        if (stancesEnteredThisTurn.contains(CalmStance.STANCE_ID)) {
            s += " #bCalm ";
        }
        if (stancesEnteredThisTurn.contains(DivinityStance.STANCE_ID)) {
            s += " #pDivinity ";
        }
        return s;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + getStancesCorrectly();
    }

    @Override
    public AbstractPower makeCopy() {
        return new DancingMasterPower(amount);
    }
}
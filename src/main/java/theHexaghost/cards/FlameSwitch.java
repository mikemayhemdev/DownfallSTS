package theHexaghost.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.RetractAction;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;
import theHexaghost.powers.BurnPower;

import java.util.ArrayList;

public class FlameSwitch extends AbstractHexaCard {
    public final static String ID = makeID("FlameSwitch");

    public FlameSwitch() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseBurn = burn = 16;
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        HexaMod.loadJokeCardImage(this, "FlameSwitch.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractPower po = m.getPower(BurnPower.POWER_ID);
                if (po != null) {
                    ((TwoAmountPower) po).amount2 += magicNumber;
                    po.updateDescription();
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(8);
        }
    }
}
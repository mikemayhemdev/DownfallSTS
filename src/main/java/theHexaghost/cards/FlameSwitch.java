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

public class FlameSwitch extends AbstractHexaCard implements OctopusCard{
    public final static String ID = makeID("FlameSwitch");

    //bad omen
    public FlameSwitch() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBurn = burn = 16;
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        HexaMod.loadJokeCardImage(this, "FlameSwitch.png");
    }

    public ArrayList<OctoChoiceCard> choiceList() {

        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoSear", this.name, HexaMod.makeCardPath("FlameSwitch.png"), this.EXTENDED_DESCRIPTION[2]));
        cardList.add(new OctoChoiceCard("octo:OctoCrush", this.name, HexaMod.makeCardPath("FlameSwitch.png"), this.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoEmpower", this.name, HexaMod.makeCardPath("FlameSwitch.png"), this.EXTENDED_DESCRIPTION[0]));

        return cardList;
    }


    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoCrush":
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        //   HexaMod.renderFlames = true;
                        isDone = true;
                        AbstractGhostflame gf = new CrushingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                        GhostflameHelper.hexaGhostFlames.set(GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame), gf);
                        gf.activate();
                    }
                });
                break;
            case "octo:OctoEmpower":
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        //  HexaMod.renderFlames = true;
                        isDone = true;
                        AbstractGhostflame gf = new BolsteringGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                        GhostflameHelper.hexaGhostFlames.set(GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame), gf);
                        gf.activate();
                    }
                });
                break;
            case "octo:OctoSear":
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        //  HexaMod.renderFlames = true;
                        isDone = true;
                        AbstractGhostflame gf = new SearingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                        GhostflameHelper.hexaGhostFlames.set(GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame), gf);
                        gf.activate();
                    }
                });
                break;
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
            selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

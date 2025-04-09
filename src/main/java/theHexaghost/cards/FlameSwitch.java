package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;

import java.util.ArrayList;

public class FlameSwitch extends AbstractHexaCard implements OctopusCard{
    public final static String ID = makeID("BadOmen");

    //bad omen
    public FlameSwitch() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
//        baseBurn = burn = 16;
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "FlameSwitch.png");
    }

    public ArrayList<OctoChoiceCard> choiceList() {

        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoSear", this.name, HexaMod.makeCardPath("BadOmen.png"), this.EXTENDED_DESCRIPTION[2]));
        cardList.add(new OctoChoiceCard("octo:OctoCrush", this.name, HexaMod.makeCardPath("BadOmen.png"), this.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoEmpower", this.name, HexaMod.makeCardPath("BadOmen.png"), this.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoNo", this.name, HexaMod.makeCardPath("BadOmen.png"), this.EXTENDED_DESCRIPTION[3]));

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

            case "octo:OctoNo":
                break;
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            isEthereal = false;
            selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

package champ.cards;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;
@NoCompendium
public class StanceDanceCrown extends AbstractChampCard {

    public final static String ID = makeID("StanceDanceCrown");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public StanceDanceCrown() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
        tags.add(ChampMod.OPENERNOTIN);
        loadJokeCardImage(this, "StanceDance.png");
        this.exhaust = true;
        this.selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.useJumpAnimation();
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        this.exhaust = false;
    }
}
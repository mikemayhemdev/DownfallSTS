/*
package downfall.cards.curses;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import collector.cards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;
@NoCompendium
@NoPools
public class Sapped extends AbstractCollectorCard {
    public final static String ID = makeID(Sapped.class.getSimpleName());
    // intellij stuff skill, none, special, , , , , 1, 1
    public Sapped() {
        super(ID, 0, CardType.CURSE, CardRarity.SPECIAL, CardTarget.NONE, CardColor.CURSE);
        baseMagicNumber = magicNumber = 1;
        isPyre();
        exhaust = true;
        SoulboundField.soulbound.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerWhenDrawn() {
        this.addToBot(new LoseEnergyAction(1));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upp() {
    }
    
}

 */

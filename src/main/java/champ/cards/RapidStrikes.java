package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;

import java.util.ArrayList;

@CardIgnore
public class RapidStrikes extends AbstractChampCard {

    public final static String ID = makeID("RapidStrikes");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 4;

    public RapidStrikes() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        // tags.add(ChampMod.FINISHER);
      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
        baseMagicNumber = magicNumber = 2;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> tackleList = new ArrayList<>();
                for (AbstractCard q : AbstractDungeon.player.hand.group) {
                    if (q.hasTag(CardTags.STRIKE) && q.cost > 0) {
                        tackleList.add(q);
                    }
                }
                if (!tackleList.isEmpty()) {
                    if (upgraded){
                        tackleList.get(AbstractDungeon.cardRandomRng.random(tackleList.size() - 1)).modifyCostForCombat(-9);


                    } else {
                        addToTop(new ReduceCostForTurnAction(tackleList.get(AbstractDungeon.cardRandomRng.random(tackleList.size() - 1)), 9));
                    }
                }
            }
        });
    }

    public void applyPowers() {
        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + (ChampMod.techniquesThisTurn + magicNumber);
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeDamage(2);
    }
}